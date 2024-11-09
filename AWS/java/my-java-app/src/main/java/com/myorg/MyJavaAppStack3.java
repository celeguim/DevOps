package com.myorg;

import java.util.Arrays;
import java.util.List;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.AuthorizeSecurityGroupIngressRequest;
import com.amazonaws.services.ec2.model.DescribeSubnetsRequest;
import com.amazonaws.services.ec2.model.DescribeSubnetsResult;
import com.amazonaws.services.ec2.model.DescribeVpcsRequest;
import com.amazonaws.services.ec2.model.DescribeVpcsResult;
import com.amazonaws.services.ec2.model.IpPermission;
import com.amazonaws.services.ec2.model.IpRange;
import com.amazonaws.services.ec2.model.RunInstancesRequest;
import com.amazonaws.services.ec2.model.RunInstancesResult;
import com.amazonaws.services.ec2.model.Subnet;
import com.amazonaws.services.ec2.model.Tag;

import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.constructs.Construct;


public class MyJavaAppStack3 extends Stack {
	public MyJavaAppStack3(final Construct scope, final String id) {
		this(scope, id, null);
	}

	public MyJavaAppStack3(final Construct scope, final String id, final StackProps props) {
		super(scope, id, props);

		// Set up your AWS credentials
		BasicAWSCredentials awsCreds = new BasicAWSCredentials("key", "secret");
		AmazonEC2 ec2Client = AmazonEC2ClientBuilder.standard()
		        .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
		        .build();

		// Describe the VPCs
		DescribeVpcsRequest describeVpcsRequest = new DescribeVpcsRequest();
		DescribeVpcsResult describeVpcsResult = ec2Client.describeVpcs(describeVpcsRequest);

		// Get the VPC ID
		String vpcId = describeVpcsResult.getVpcs().get(0).getVpcId();
		System.out.printf("vpcId: %s %n", vpcId);

		// Get Public Subnet
		String publicSubnetId = null; 
		DescribeSubnetsRequest describeSubnetsRequest = new DescribeSubnetsRequest();
		DescribeSubnetsResult describeSubnetsResult = ec2Client.describeSubnets(describeSubnetsRequest);
		List<Subnet> subnets = describeSubnetsResult.getSubnets();
		for (Subnet subnet: subnets) {
			List<Tag> tags = subnet.getTags();
			for (Tag tag: tags) {
				System.out.printf("Tag: %s %n", tag);
				if (tag.getKey().equalsIgnoreCase("Name")) {
					if (tag.getValue().equals("my-public-subnet")) {
						publicSubnetId = subnet.getSubnetId();
					}
				}
			}
		}

		System.out.printf("my-public-subnet: %s %n", publicSubnetId);

		
		// Create a key pair
//		CreateKeyPairRequest keyPairRequest = new CreateKeyPairRequest();
//		keyPairRequest.setKeyName("my-key-pair");
//		CreateKeyPairResult keyPairResult = ec2Client.createKeyPair(keyPairRequest);
//		String keyPairId = keyPairResult.getKeyPair().getKeyPairId();
//		System.out.printf("keyPairId %s", keyPairId);
//		DescribeKeyPairsRequest describeKeyPairsRequest = new DescribeKeyPairsRequest();
//		describeKeyPairsRequest.setKeyNames(Arrays.asList("my-key-pair"));
//		DescribeKeyPairsResult describeKeyPairsResult = ec2Client.describeKeyPairs(describeKeyPairsRequest);
//		for (KeyPairInfo keyPairInfo : describeKeyPairsResult.getKeyPairs()) {
//		    System.out.println("Key Pair Name: " + keyPairInfo.getKeyName());
//		    System.out.println("Key Pair Fingerprint: " + keyPairInfo.getKeyFingerprint());
//		}
		
		// Create a security group
//		CreateSecurityGroupRequest securityGroupRequest = new CreateSecurityGroupRequest();
//		securityGroupRequest.setGroupName("my-security-group");
//		securityGroupRequest.setDescription("My security group");
//		securityGroupRequest.setVpcId(vpcId);
//		CreateSecurityGroupResult securityGroupResult = ec2Client.createSecurityGroup(securityGroupRequest);
//		String securityGroupId = securityGroupResult.getGroupId();
		String securityGroupId = "sg-00edab8f1b408879d";
		
		// Add a rule to the security group to allow inbound traffic on port 22
		IpPermission ipPermission = new IpPermission();
		ipPermission.setIpProtocol("tcp");
		ipPermission.setFromPort(22);
		ipPermission.setToPort(22);
		IpRange ipRange = new IpRange();
		ipRange.setCidrIp("0.0.0.0/0");
		ipPermission.setIpv4Ranges(Arrays.asList(ipRange));
		ec2Client.authorizeSecurityGroupIngress(new AuthorizeSecurityGroupIngressRequest()
		        .withGroupId(securityGroupId)
		        .withIpPermissions(ipPermission));
		
		// Run the EC2 instance
		RunInstancesRequest runInstancesRequest = new RunInstancesRequest();
		runInstancesRequest.setInstanceType("t3.nano");
		runInstancesRequest.setImageId("ami-0182f373e66f89c85"); 
		runInstancesRequest.setKeyName("my-key-pair");
		runInstancesRequest.setMinCount(1);
		runInstancesRequest.setMaxCount(1);
		runInstancesRequest.setSecurityGroupIds(Arrays.asList(securityGroupId));
		runInstancesRequest.setSubnetId(publicSubnetId);
		
		RunInstancesResult runInstancesResult = ec2Client.runInstances(runInstancesRequest);
		String instanceId = runInstancesResult.getReservation().getInstances().get(0).getInstanceId();
		System.out.printf("instanceId %s", instanceId);
		
	}
}

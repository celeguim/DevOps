package com.myorg;

import java.util.Arrays;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.AssociateRouteTableRequest;
import com.amazonaws.services.ec2.model.AttachInternetGatewayRequest;
import com.amazonaws.services.ec2.model.CreateInternetGatewayRequest;
import com.amazonaws.services.ec2.model.CreateInternetGatewayResult;
import com.amazonaws.services.ec2.model.CreateRouteRequest;
import com.amazonaws.services.ec2.model.CreateRouteTableRequest;
import com.amazonaws.services.ec2.model.CreateRouteTableResult;
import com.amazonaws.services.ec2.model.CreateSubnetRequest;
import com.amazonaws.services.ec2.model.CreateSubnetResult;
import com.amazonaws.services.ec2.model.CreateVpcRequest;
import com.amazonaws.services.ec2.model.CreateVpcResult;

import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.constructs.Construct;
import com.amazonaws.services.ec2.model.CreateTagsRequest;
import com.amazonaws.services.ec2.model.Tag;


public class MyJavaAppStack2 extends Stack {
	public MyJavaAppStack2(final Construct scope, final String id) {
		this(scope, id, null);
	}

	public MyJavaAppStack2(final Construct scope, final String id, final StackProps props) {
		super(scope, id, props);


		// Create a new VPC by BlackBox AI
		// Set up your AWS credentials
		BasicAWSCredentials awsCreds = new BasicAWSCredentials("key", "secret");
		AmazonEC2 ec2Client = AmazonEC2ClientBuilder.standard()
		        .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
		        .build();
		
		CreateVpcRequest createVpcRequest = new CreateVpcRequest("10.0.0.0/16");
		CreateVpcResult createVpcResult = ec2Client.createVpc(createVpcRequest);

		String vpcId = createVpcResult.getVpc().getVpcId();

		// Create a tag
		CreateTagsRequest createTagsRequest = new CreateTagsRequest();
		createTagsRequest.setResources(Arrays.asList(vpcId));
		createTagsRequest.setTags(Arrays.asList(new Tag("Name", "my-vpc")));
		ec2Client.createTags(createTagsRequest);

		// Create a public subnet
		CreateSubnetRequest publicSubnetRequest = new CreateSubnetRequest();
		publicSubnetRequest.setVpcId(vpcId);
		publicSubnetRequest.setCidrBlock("10.0.1.0/24");
		publicSubnetRequest.setAvailabilityZone("us-east-1a");

		CreateSubnetResult publicSubnetResult = ec2Client.createSubnet(publicSubnetRequest);
		String publicSubnetId = publicSubnetResult.getSubnet().getSubnetId();
		
		// Create a tag
		createTagsRequest = new CreateTagsRequest();
		createTagsRequest.setResources(Arrays.asList(publicSubnetId));
		createTagsRequest.setTags(Arrays.asList(new Tag("Name", "my-public-subnet")));
		ec2Client.createTags(createTagsRequest);
		
		// Create a private subnet
		CreateSubnetRequest privateSubnetRequest = new CreateSubnetRequest();
		privateSubnetRequest.setVpcId(vpcId);
		privateSubnetRequest.setCidrBlock("10.0.2.0/24");
		privateSubnetRequest.setAvailabilityZone("us-east-1a");

		CreateSubnetResult privateSubnetResult = ec2Client.createSubnet(privateSubnetRequest);
		String privateSubnetId = privateSubnetResult.getSubnet().getSubnetId();

		// Create a tag
		createTagsRequest = new CreateTagsRequest();
		createTagsRequest.setResources(Arrays.asList(privateSubnetId));
		createTagsRequest.setTags(Arrays.asList(new Tag("Name", "my-private-subnet")));
		ec2Client.createTags(createTagsRequest);

		// Create an internet gateway
		CreateInternetGatewayRequest internetGatewayRequest = new CreateInternetGatewayRequest();
		CreateInternetGatewayResult internetGatewayResult = ec2Client.createInternetGateway(internetGatewayRequest);
		String internetGatewayId = internetGatewayResult.getInternetGateway().getInternetGatewayId();

		// Attach the internet gateway to the VPC
		AttachInternetGatewayRequest attachInternetGatewayRequest = new AttachInternetGatewayRequest();
		attachInternetGatewayRequest.setInternetGatewayId(internetGatewayId);
		attachInternetGatewayRequest.setVpcId(vpcId);
		ec2Client.attachInternetGateway(attachInternetGatewayRequest);

		// Create a route table
		CreateRouteTableRequest routeTableRequest = new CreateRouteTableRequest();
		routeTableRequest.setVpcId(vpcId);
		CreateRouteTableResult routeTableResult = ec2Client.createRouteTable(routeTableRequest);
		String routeTableId = routeTableResult.getRouteTable().getRouteTableId();

		// Create a route to the internet gateway
		CreateRouteRequest routeRequest = new CreateRouteRequest();
		routeRequest.setDestinationCidrBlock("0.0.0.0/0");
		routeRequest.setGatewayId(internetGatewayId);
		routeRequest.setRouteTableId(routeTableId);
		ec2Client.createRoute(routeRequest);

		// Associate the route table with the public subnet
		AssociateRouteTableRequest associateRouteTableRequest = new AssociateRouteTableRequest();
		associateRouteTableRequest.setSubnetId(publicSubnetId);
		associateRouteTableRequest.setRouteTableId(routeTableId);
		ec2Client.associateRouteTable(associateRouteTableRequest);
		
		
		
		
		
		/////////////////////////////////////////////////
//		// Create a new VPC
//		// Public Subnet
//		SubnetConfiguration myPubSubNet =
//		SubnetConfiguration.builder()
//		.name("my_public_subnet")
//		.subnetType(SubnetType.PUBLIC).build();
//
//		// Private/Isolated Subnet
//		SubnetConfiguration myPrivSubNet =
//		SubnetConfiguration.builder()
//		.name("my_private_subnet")
//		.subnetType(SubnetType.PRIVATE_ISOLATED).build();
//
//		Vpc vpc = Vpc.Builder.create(this, "MyVpc")
//				.ipAddresses(IpAddresses.cidr ("10.0.0.0/16"))
//				.enableDnsHostnames(true)
//				.enableDnsSupport(true)
//				.natGateways(0)
//				.subnetConfiguration(List.of(myPubSubNet,myPrivSubNet))
//				.build();

	}
}

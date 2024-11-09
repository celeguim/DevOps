package com.myorg;

import java.util.List;

import software.amazon.awscdk.RemovalPolicy;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.ec2.IpAddresses;
import software.amazon.awscdk.services.ec2.SubnetConfiguration;
import software.amazon.awscdk.services.ec2.SubnetType;
import software.amazon.awscdk.services.ec2.Vpc;
import software.constructs.Construct;


public class MyJavaAppStack4 extends Stack {
	public MyJavaAppStack4(final Construct scope, final String id) {
		this(scope, id, null);
	}

	public MyJavaAppStack4(final Construct scope, final String id, final StackProps props) {
		super(scope, id, props);
		
		/////////////////////////////////////////////////
		// Create a new VPC
		// Public Subnet
		SubnetConfiguration myPubSubNet = SubnetConfiguration.builder()
				.name("my_public_subnet")
				.subnetType(SubnetType.PUBLIC)
				.build();

		// Private/Isolated Subnet
		SubnetConfiguration myPrivSubNet = SubnetConfiguration.builder()
				.name("my_private_subnet")
				.subnetType(SubnetType.PRIVATE_ISOLATED)
				.build();

		Vpc vpc = Vpc.Builder.create(this, "my-vpc")
				.ipAddresses(IpAddresses.cidr ("10.0.0.0/16"))
				.enableDnsHostnames(true)
				.enableDnsSupport(true)
				.natGateways(0)
				.subnetConfiguration(List.of(myPubSubNet, myPrivSubNet))
				.build();
		
		vpc.applyRemovalPolicy(RemovalPolicy.DESTROY);
		
	}
}

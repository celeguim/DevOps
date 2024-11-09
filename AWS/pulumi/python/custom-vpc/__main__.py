"""An AWS Python Pulumi program"""

import pulumi
from pulumi_aws import ec2

# Create a VPC
vpc = ec2.Vpc("my-vpc", cidr_block="10.0.0.0/16", tags={"Name":"my-vpc"})

# Create an Internet Gateway
igw = ec2.InternetGateway("my-igw", tags={"Name":"my-igw"})

# Attach the Internet Gateway to the VPC
vpc_gateway_attachment = ec2.InternetGatewayAttachment("my-vpc-gateway-attachment",
    vpc_id=vpc.id,
    internet_gateway_id=igw.id
)

# Create a public subnet
public_subnet = ec2.Subnet("my-public-subnet",
    vpc_id=vpc.id,
    cidr_block="10.0.1.0/24",
    availability_zone="us-east-1a",
    map_public_ip_on_launch=True,
    tags={"Name":"my-public-subnet"}
)

# Create a private subnet
private_subnet = ec2.Subnet("my-private-subnet",
    vpc_id=vpc.id,
    cidr_block="10.0.2.0/24",
    availability_zone="us-east-1a",
    tags={"Name": "my-private-subnet"}
)

# Create a route table for the public subnet
public_route_table = ec2.RouteTable("my-public-route-table",
    vpc_id=vpc.id,
)

# Create a route for the public subnet to the Internet Gateway
public_route = ec2.Route("my-public-route",
    route_table_id=public_route_table.id,
    destination_cidr_block="0.0.0.0/0",
    gateway_id=igw.id,
)

# Associate the public subnet with the public route table
public_subnet_route_table_association = ec2.RouteTableAssociation("my-public-subnet-route-table-association",
    subnet_id=public_subnet.id,
    route_table_id=public_route_table.id,
)

# Create a route table for the private subnet
private_route_table = ec2.RouteTable("my-private-route-table",
    vpc_id=vpc.id,
)

# Associate the private subnet with the private route table
private_subnet_route_table_association = ec2.RouteTableAssociation("my-private-subnet-route-table-association",
    subnet_id=private_subnet.id,
    route_table_id=private_route_table.id,
)

# Create a security group for SSH access
ssh_security_group = ec2.SecurityGroup("my-ssh-sg",
    vpc_id=vpc.id,
    description="Allow SSH access from anywhere",
)

# Add a rule to allow incoming SSH traffic
ssh_rule = ec2.SecurityGroupRule("my-ssh-rule",
    security_group_id=ssh_security_group.id,
    type="ingress",
    from_port=22,
    to_port=22,
    protocol="tcp",
    cidr_blocks=["0.0.0.0/0"],
)

# Allow outbound traffic on port 80
http_sg = ec2.SecurityGroupRule("my-http-rule",
    type="egress",
    from_port=80,
    to_port=80,
    protocol="tcp",
    cidr_blocks=["0.0.0.0/0"],
    security_group_id=ssh_security_group.id,
)

# Allow outbound traffic on port 80
https_sg = ec2.SecurityGroupRule("my-https-rule",
    type="egress",
    from_port=443,
    to_port=443,
    protocol="tcp",
    cidr_blocks=["0.0.0.0/0"],
    security_group_id=ssh_security_group.id,
)

# Create a user data script that updates packages
user_data = """#!/bin/bash
sudo yum update -y
sudo yum install python3 -y
"""

# Create an EC2 instance
instance = ec2.Instance("my-instance",
    ami="ami-0182f373e66f89c85",  # Replace with the ID of the desired AMI
    instance_type="t3.nano",
    vpc_security_group_ids=[ssh_security_group.id],
    subnet_id=public_subnet.id,
    key_name="my-key-pair",  # Replace with the name of your key pair
    user_data=user_data,
    tags = {"Name": "my-instance"},
)

pulumi.export('instance', instance)
pulumi.export('public_subnet', public_subnet)

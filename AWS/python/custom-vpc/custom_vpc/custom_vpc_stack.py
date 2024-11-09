from aws_cdk import (
    Stack, aws_ec2 as ec2, Tags
)
from constructs import Construct

class CustomVpcStack(Stack):

    def __init__(self, scope: Construct, construct_id: str, **kwargs) -> None:
        super().__init__(scope, construct_id, **kwargs)

        vpc = ec2.Vpc(
            self, "MyVpc",
            cidr="10.13.0.0/21",
            max_azs=2,
            nat_gateways=0,
            subnet_configuration=[
                ec2.SubnetConfiguration(name="public", cidr_mask=24, subnet_type=ec2.SubnetType.PUBLIC),
                # ec2.SubnetConfiguration(name="private", cidr_mask=24, subnet_type=ec2.SubnetType.PRIVATE_WITH_EGRESS),
                ec2.SubnetConfiguration(name="private", cidr_mask=24, subnet_type=ec2.SubnetType.PRIVATE_ISOLATED)
            ]
        )

        # Tag all VPC Resources
        # Tags.add(vpc, key="Owner", value="KonStone", include_resource_types=[])

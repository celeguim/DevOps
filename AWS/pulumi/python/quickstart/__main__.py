"""An AWS Python Pulumi program"""

import pulumi
import pulumi_awsx as awsx

vpc = awsx.ec2.Vpc("default-vpc")
vpc.cidr_block = ""

pulumi.export("vpcId", vpc.vpc_id)
pulumi.export("private_subnet_ids", vpc.private_subnet_ids)
pulumi.export("public_subnet_ids", vpc.public_subnet_ids)

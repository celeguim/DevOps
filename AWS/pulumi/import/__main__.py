"""An AWS Python Pulumi program"""

import pulumi
from pulumi_aws import s3
import pulumi_aws as aws

my_import = aws.s3.Bucket("my-import",
    arn="arn:aws:s3:::cdk-hnb659fds-assets-958661899676-us-east-1",
    bucket="cdk-hnb659fds-assets-958661899676-us-east-1",
    hosted_zone_id="Z3AQBSTGFYJSTF",
    lifecycle_rules=[{
        "enabled": True,
        "id": "CleanupOldVersions",
        "noncurrent_version_expiration": {
            "days": 365,
        },
    }],
    request_payer="BucketOwner",
    server_side_encryption_configuration={
        "rule": {
            "apply_server_side_encryption_by_default": {
                "sse_algorithm": "aws:kms",
            },
        },
    },
    versioning={
        "enabled": True,
    },
    opts = pulumi.ResourceOptions(protect=True))

# Create an AWS resource (S3 Bucket)
bucket = s3.Bucket('my-bucket')

# Export the name of the bucket
pulumi.export('bucket_name', bucket.id)


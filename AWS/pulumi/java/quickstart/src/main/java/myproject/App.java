package myproject;

import com.pulumi.Pulumi;
import com.pulumi.aws.ec2.Vpc;
import com.pulumi.aws.ec2.VpcArgs;
import com.pulumi.aws.s3.Bucket;
import com.pulumi.core.Output;

public class App {
	public static void main(String[] args) {
		Pulumi.run(ctx -> {
			var bucket = new Bucket("my-bucket");
			ctx.export("bucketName", bucket.bucket());

			Vpc vpc = new Vpc("my-vpc", VpcArgs.builder()
					.cidrBlock("10.0.0.0/16")
					.build());

			Output<String> vpcId = vpc.id();
			
		});
	}
}

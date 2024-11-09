package com.myorg;

import software.amazon.awscdk.App;
import software.amazon.awscdk.StackProps;

public class MyEc2InstanceApp {
	public static void main(final String[] args) {
		App app = new App();
		new MyEc2InstanceStack(app, "MyEc2InstanceStack", StackProps.builder().build());
		app.synth();
	}
}

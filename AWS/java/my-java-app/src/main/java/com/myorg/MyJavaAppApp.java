package com.myorg;

import software.amazon.awscdk.App;
import software.amazon.awscdk.StackProps;

public class MyJavaAppApp {
	public static void main(final String[] args) {
		App app = new App();

		String stack = app.getNode().getContext("stack").toString();
		System.out.printf("stack: %s %n", stack);
		
		if (stack.equals("MyJavaAppStack1")) {
			new MyJavaAppStack1(app, "MyJavaAppStack1", StackProps.builder().build());
			app.synth();
		}

		if (stack.equals("MyJavaAppStack2")) {
			new MyJavaAppStack2(app, "MyJavaAppStack2", StackProps.builder().build());
			app.synth();
		}

		if (stack.equals("MyJavaAppStack3")) {
			new MyJavaAppStack3(app, "MyJavaAppStack3", StackProps.builder().build());
			app.synth();
		}

		if (stack.equals("MyJavaAppStack4")) {
			new MyJavaAppStack4(app, "MyJavaAppStack4", StackProps.builder().build());
			app.synth();
		}

	}
}

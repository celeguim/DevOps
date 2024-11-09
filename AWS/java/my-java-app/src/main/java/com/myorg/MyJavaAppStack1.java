package com.myorg;

import software.amazon.awscdk.CfnOutput;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.lambda.Code;
import software.amazon.awscdk.services.lambda.Function;
import software.amazon.awscdk.services.lambda.FunctionUrl;
import software.amazon.awscdk.services.lambda.FunctionUrlAuthType;
import software.amazon.awscdk.services.lambda.FunctionUrlOptions;
import software.amazon.awscdk.services.lambda.Runtime;
import software.constructs.Construct;

public class MyJavaAppStack1 extends Stack {
    public MyJavaAppStack1(final Construct scope, final String id) {
        this(scope, id, null);
    }

    public MyJavaAppStack1(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);
        
        Function myFunction = Function.Builder.create(this, "HelloWorldFunction")
        		.runtime(Runtime.NODEJS_20_X)
        		.handler("index.handler")
        		.code(Code.fromInline(
        				"exports.handler = async function(event) {" +
        						"return {" +
        						" statusCode: 200," +
        						" body: JSON.stringify('Hello World !')" +
        						"};" +
        						"};"
        				))
        		.build();
        
        
        FunctionUrl url = myFunction.addFunctionUrl(FunctionUrlOptions.builder()
        		.authType(FunctionUrlAuthType.NONE)
        		.build());
        
        CfnOutput.Builder.create(this, "myFuncUrl")
        .value(url.getUrl())
        .build();
    }
}

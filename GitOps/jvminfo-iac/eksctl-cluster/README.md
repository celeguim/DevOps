# Create EKS cluster using eksctl

```
EKS_NAME=eksctl-cluster1
EKS_REGION=us-east-1

# 1. Create Management Cluster Only (no nodes)
eksctl create cluster --name=${EKS_NAME} \
                      --region=${EKS_REGION} \
                      --zones=us-east-1a,us-east-1b \
                      --without-nodegroup 

eksctl get cluster                  

# 2. Create key-pair

# 3. Associate OIDC
eksctl utils associate-iam-oidc-provider \
    --region ${EKS_REGION} \
    --cluster ${EKS_NAME} \
    --approve

# 4. Create Public Node Group   
eksctl create nodegroup --cluster=${EKS_NAME} \
                        --region=${EKS_REGION} \
                        --name=${EKS_NAME}-ng-public1 \
                        --node-type=t2.small \
                        --nodes=1 \
                        --nodes-min=1 \
                        --nodes-max=2 \
                        --node-volume-size=20 \
                        --ssh-access \
                        --ssh-public-key=my-key-pair \
                        --managed \
                        --asg-access \
                        --external-dns-access \
                        --full-ecr-access \
                        --appmesh-access \
                        --alb-ingress-access 

# 5. Check cluster configurations

# 6. Delete cluster
eksctl delete cluster --name=${EKS_NAME} \
                      --region=${EKS_REGION} \


```

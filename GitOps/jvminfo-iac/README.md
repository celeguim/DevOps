# Terraform code 

## Maintain VPC & EKS with Terraform

## Tools required
Terraform v1.5.7

### Steps
* terraform init
* terraform fmt -check
* terraform validate
* terraform plan -out planfile
* terraform apply -auto-approve -input=false -parallelism=1 planfile

### update kubernetes config
* aws eks update-kubeconfig --region us-east-2 --name jvminfo-eks

### clean up
* kubectl delete -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/controller-v1.3.0/deploy/static/provider/aws/deploy.yaml

* helm list
* helm uninstall jvminfo-eks
* download terraform.tfstate from bucket jvminfo-terraform-state
* terraform init -backend-config="bucket=jvminfo-terraform-state"
* terraform destroy

####
#####

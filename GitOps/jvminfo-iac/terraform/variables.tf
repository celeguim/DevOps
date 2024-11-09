variable "region" {
  description = "AWS region"
  type        = string
  default     = "us-east-2"
}

variable "clusterName" {
  description = "EKS cluster name"
  type        = string
  default     = "jvminfo-eks"
}


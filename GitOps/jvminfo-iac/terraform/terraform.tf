terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.73.0"
    }

    random = {
      source  = "hashicorp/random"
      version = "~> 3.6.3"
    }

    tls = {
      source  = "hashicorp/tls"
      version = "~> 4.0.6"
    }

    cloudinit = {
      source  = "hashicorp/cloudinit"
      version = "~> 2.3.5"
    }

    kubernetes = {
      source  = "hashicorp/kubernetes"
      version = "~> 2.33.0"
    }
  }

  backend "s3" {
    bucket = "jvminfo-terraform-state"
    key    = "terraform.tfstate"
    region = "us-east-2"
  }

  # required_version = "~> 1.5.7"
}

###

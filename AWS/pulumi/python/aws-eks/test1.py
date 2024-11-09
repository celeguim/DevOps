import pulumi

# Get some values from the Pulumi configuration (or use defaults)
config = pulumi.Config()
min_cluster_size = config.get_int("minClusterSize", 3)
max_cluster_size = config.get_int("maxClusterSize", 6)
desired_cluster_size = config.get_int("desiredClusterSize", 3)
eks_node_instance_type = config.get("eksNodeInstanceType", "t3.medium")
vpc_network_cidr = config.get("vpcNetworkCidr", "10.0.0.0/16")

print(config.get("minClusterSize"))

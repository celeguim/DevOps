def greeting(msg):
  print("Good %s".format(msg))

from fabric import Connection
result = Connection('celeghin@192.168.1.183').run('uname -s', hide=False)
msg = "Ran {0.command!r} on {0.connection.host}, got stdout:\n{0.stdout}"
print(msg.format(result))

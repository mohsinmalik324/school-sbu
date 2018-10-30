import socket

serversocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
host = ''
port = 8080
serversocket.bind((host, port))
serversocket.listen(1)
print('Server running!')

clientsocket,addr = serversocket.accept()
print('Connection established!')

d = {}
protocol = 'HTTP/1.1'
e200 = '200 OK\n'
e220 = '220 UNSUPPORTED\n'
e400 = '400 BAD_REQUEST\n'
e404 = '404 NOT FOUND\n'
bad_args = protocol + ' ' + e400

while True:
	msg = clientsocket.recv(256)
	msg = msg.decode()
	msg = msg[:-2]
	#print("'" + msg + "'")
	if msg == 'QUIT':
		clientsocket.send('Goodbye!\n'.encode())
		break
	args = msg.split(' ')
	resp = protocol + ' ';
	if args[0] == 'GET':
		if len(args) == 2:
			if args[1] in d:
				resp += e200 + '\n' + d[args[1]] + '\n';
			else:
				resp += e404
		else:
			resp = bad_args
	elif args[0] == 'PUT':
		if len(args) == 3:
			d[args[1]] = args[2]
			resp += e200
		else:
			resp = bad_args
	elif args[0] == 'DELETE':
		if len(args) == 2:
			try:
				del d[args[1]]
			except KeyError:
				pass
			resp += e200
		else:
			resp = bad_args
	elif args[0] == 'CLEAR':
		if len(args) == 1:
			d = {}
			resp += e200
		else:
			resp = bad_args
	else:
		resp += e220

	clientsocket.send(resp.encode())

clientsocket.close()
serversocket.close()

print('Goodbye!')

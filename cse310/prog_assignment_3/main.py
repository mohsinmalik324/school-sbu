filename = input('File name: ')

edges = []
nodes = []
n_count = 0

with open(filename, 'r') as file:
    for line in file:
        line = line[:-1]
        line_split = line.split(',')
        source = line_split[0]
        dest = line_split[1]
        weight = int(line_split[2])
        edges.append((source, dest, weight))
        if source not in nodes:
            nodes.append(source)
        if dest not in nodes:
            nodes.append(dest)

n = len(nodes)
e = len(edges)
d = {}
p = {}
INF = 999999

for i in range(0, n):
    node = nodes[i]
    d[node] = INF
    p[node] = 'x'

d['x'] = 0

for i in range(0, n - 1):
    for j in range(0, e):
        edge = edges[j]
        src = edge[0]
        dest = edge[1]
        weight = edge[2]
        if d[src] != INF and d[dest] > d[src] + weight:
            d[dest] = d[src] + weight
            p[dest] = src

path = 'y'
cur = 'y'
while cur != 'x':
    next = p[cur]
    path += '<-' + next
    cur = next

def reverse(s):
    str = ''
    for i in s:
        str = i + str
    return str

path = reverse(path)
path = path.replace('<', '>')

print(path)
print('Cost: ' + str(d['y']))

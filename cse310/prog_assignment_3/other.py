edges = []
edges.append(('x', 'a', 9))
edges.append(('x', 'c', 14))
edges.append(('x', 'd', 15))
edges.append(('a', 'b', 24))
edges.append(('c', 'b', 18))
edges.append(('c', 'e', 30))
edges.append(('c', 'd', 5))
edges.append(('d', 'e', 20))
edges.append(('d', 'y', 44))
edges.append(('e', 'f', 11))
edges.append(('e', 'y', 16))
edges.append(('f', 'b', 6))
edges.append(('f', 'y', 6))
edges.append(('b', 'e', 2))
edges.append(('b', 'y', 19))

nodes = {'x': 0, 'a': 1, 'b': 2, 'c': 3, 'd': 4, 'e': 5, 'f': 6, 'y': 7}

n = 8
e = len(edges)
d = []
p = []
for i in range(0, n):
    d.append(999999)
    p.append(0)

d[0] = 0

for i in range(0, n - 1):
    for j in range(0, e):
        edge = edges[j]
        u = nodes[edge[0]]
        v = nodes[edge[1]]
        w = edge[2]
        if d[u] != 999999 and d[v] > d[u] + w:
            d[v] = d[u] + w
            p[v] = u

print(d)
print(p)

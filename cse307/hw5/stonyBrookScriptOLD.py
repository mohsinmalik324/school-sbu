#Mohsin Malik 110880864

import ply.lex as lex
import ply.yacc as yacc
import sys
from numbers import Number

def is_number(x):
	return isinstance(x, Number)
	
def is_list(x):
	return type(x) is list
	
def is_str(x):
	return isinstance(x, str)
	
def get_type(x):
	if is_number(x):
		return 1
	elif is_str(x):
		return 2
	elif is_list(x):
		return 3
	else:
		return -1

class Node:
	def __init__(self):
		print("init node")
		
	def evaluate(self):
		return 0
		
	def execute(self):
		return 0
		
class ValueNode(Node):
	def __init__(self, v):
		self.value = v
	
	def evaluate(self):
		ret = self.value
		if is_list(ret):
			tmp = []
			for node in ret:
				tmp = tmp + [node.evaluate()]
			ret = tmp
		return ret
		
class NotNode(Node):
	def __init__(self, v):
		self.value = v;
		
	def evaluate(self):
		return not self.value.evaluate()
		
class BopNode(Node):
	def __init__(self, op, v1, v2):
		self.v1 = v1
		self.v2 = v2
		self.op = op
		
	def evaluate(self):
		if self.op == "+":
			return self.v1.evaluate() + self.v2.evaluate()
		elif self.op == "-":
			return self.v1.evaluate() - self.v2.evaluate()
		elif self.op == "/":
			return self.v1.evaluate() / self.v2.evaluate()
		elif self.op == "*":
			return self.v1.evaluate() * self.v2.evaluate()
		elif self.op == "//":
			return self.v1.evaluate() // self.v2.evaluate()
		elif self.op == "**":
			return self.v1.evaluate() ** self.v2.evaluate()
		elif self.op == "%":
			return self.v1.evaluate() % self.v2.evaluate()
		elif self.op == "in":
			return self.v1.evaluate() in self.v2.evaluate()
		elif self.op == "and":
			return self.v1.evaluate() and self.v2.evaluate()
		elif self.op == "or":
			return self.v1.evaluate() or self.v2.evaluate()
		elif self.op == ">":
			return self.v1.evaluate() > self.v2.evaluate()
		elif self.op == ">=":
			return self.v1.evaluate() >= self.v2.evaluate()
		elif self.op == "<":
			return self.v1.evaluate() < self.v2.evaluate()
		elif self.op == "<=":
			return self.v1.evaluate() <= self.v2.evaluate()
		elif self.op == "==":
			return self.v1.evaluate() == self.v2.evaluate()
		elif self.op == "<>":
			return not self.v1.evaluate() == self.v2.evaluate()
			
class IndexNode(Node):
	def __init__(self, v, index):
		self.value = v
		self.index = index
		
	def evaluate(self):
		ret = 0
		try:
			ret = self.value.evaluate()[self.index.evaluate()]
		except IndexError:
			print("SEMANTIC ERROR")
		except TypeError:
			print("SEMANTIC ERROR")
		return ret;
			
class PrintNode(Node):
	def __init__(self, v):
		self.value = v
		
	def execute(self):
		self.value = self.value.evaluate()
		print(self.value)
		
class AssignNode(Node):
	def __init__(self, name, v):
		self.name = name
		self.value = v
		
	def execute(self):
		vars[self.name] = self.value.evaluate()
		
class BlockNode(Node):
	def __init__(self, s):
		self.sl = [s]
		
	def execute(self):
		for statement in self.sl:
			statement.execute()
			
usefile = True

if len(sys.argv) < 2:
	usefile = False

reserved = {
	'print' : 'PRINT'
}
	
tokens = [
	'NUMBER', 'BOOLEAN', 'STRING',
	'LBRACKET', 'RBRACKET', 'LPAREN', 'RPAREN', 'LBRACE', 'RBRACE',
	'ADD', 'MINUS', 'MULT', 'DIVIDE', 'FDIVIDE', 'MOD', 'POW',
	'IN', 'LT', 'GT', 'EQUAL', 'NEQUAL', 'LTE', 'GTE',
	'NOT', 'AND', 'OR', 'COMMA', 'SEMI', 'NAME'
] + list(reserved.values())

t_LBRACKET = r'\['
t_RBRACKET = r'\]'
t_LPAREN = r'\('
t_RPAREN = r'\)'
t_LBRACE = r'\{'
t_RBRACE = r'\}'
t_ADD = r'\+'
t_MINUS = r'\-'
t_POW = r'\*\*'
t_MULT = r'\*'
t_FDIVIDE = r'\/\/'
t_DIVIDE = r'\/'
t_MOD = r'\%'
t_IN = r'in'
t_EQUAL = r'\='
t_NEQUAL = r'\<\>'
t_LTE = r'\<\='
t_LT = r'\<'
t_GTE = r'\>\='
t_GT = r'\>'
t_NOT = r'not'
t_AND = r'and'
t_OR = r'or'
t_COMMA = r','
t_SEMI = r';'

t_ignore = r' '

def t_NAME(t):
	r'[A-Za-z][A-Za-z0-9_]*'
	t.type = reserved.get(t.value, 'NAME')
	return t

def t_NUMBER(t):
	r'[+-]?([0-9]*[.])?[0-9]+|\d+'
	if '.' in t.value:
		t.value = ValueNode(float(t.value))
	else:
		t.value = ValueNode(int(t.value))
	return t
	
def t_BOOLEAN(t):
	r'(True|False)'
	if t.value == "True":
		t.value = ValueNode(True)
	else:
		t.value = ValueNode(False)
	return t
	
def t_STRING(t):
	r"\"([^\\\"]|\\.)*\"|'([^\\']|\\.)*'"
	t.value = ValueNode(t.value[1:len(t.value) - 1])
	return t
	
def t_error(t):
	print("SYNTAX ERROR")
	#t.lexer.skip(len(t.value))

lexer = lex.lex()

#lexer.input("print(1);")

#while True:
#	tok = lexer.token()
#	if not tok:
#		break
#	print(tok)

#sys.exit()

vars = {}

precedence = (
	("left", "OR"),
	("left", "AND"),
	("left", "NOT"),
	("left", "GT", "GTE", "LT", "LTE", "EQUAL", "NEQUAL"),
	("left", "IN"),
	("left", "ADD", "MINUS"),
	("left", "FDIVIDE"),
	("left", "MOD"),
	("left", "MULT", "DIVIDE"),
	("right", "POW")
)

def p_block(p):
	'''
	block : LBRACE inblock RBRACE
	'''
	p[0] = p[2]
	
def p_inblock(p):
	'''
	inblock : smt inblock
	'''
	p[0] = p[2]
	p[0].sl.insert(0, p[1])
	
def p_inblock2(p):
	'''
	inblock : smt
	'''
	p[0] = BlockNode(p[1])
	
def p_smt(p):
	'''
	smt : print_smt
		| assign_smt
	'''
	p[0] = p[1]
	
def p_assign_smt(p):
	'''
	assign_smt : NAME EQUAL expression SEMI
	'''
	p[0] = AssignNode(p[1], p[3])
	
def p_print_smt(p):
	'''
	print_smt : PRINT LPAREN expression RPAREN SEMI
	'''
	p[0] = PrintNode(p[3])

def p_expression(p):
	'''
	expression : NUMBER
			   | BOOLEAN
			   | STRING
			   | list
	'''
	p[0] = p[1]
	
def p_expression2(p):
	'''
	expression : LPAREN expression RPAREN
	'''
	p[0] = p[2]
	
def p_index(p):
	'''
	expression : expression LBRACKET expression RBRACKET
	'''
	p[0] = IndexNode(p[1], p[3])
	
def p_not(p):
	'''
	expression : NOT expression
	'''
	p[0] = NotNode(p[2])
	
def p_bops(p):
	'''
	expression : expression AND expression
			   | expression OR expression
			   | expression GT expression
			   | expression GTE expression
			   | expression LT expression
			   | expression LTE expression
			   | expression EQUAL expression
			   | expression NEQUAL expression
			   | expression ADD expression
			   | expression MINUS expression
			   | expression MULT expression
			   | expression DIVIDE expression
			   | expression FDIVIDE expression
			   | expression POW expression
			   | expression MOD expression
			   | expression IN expression
	'''
	p[0] = BopNode(p[2], p[1], p[3])
		
def p_list(p):
	'''
	list : LBRACKET RBRACKET
		 | LBRACKET innerlist RBRACKET
	'''
	if p[2] == "]":
		p[0] = ValueNode([])
	else:
		p[0] = ValueNode(p[2])
		
def p_innerlist(p):
	'''
	innerlist : innerlist COMMA expression
			  | expression
	'''
	if len(p) == 2:
		p[0] = [p[1]]
	else:
		p[0] = p[1] + [p[3]]
	
def p_error(p):
	print("SYNTAX ERROR")
	#while True:
	#	tok = parser.token()
	#	if not tok:
	#		break;
	
parser = yacc.yacc()

if usefile == True:
	filename = sys.argv[1]

	with open(filename) as f:
		content = f.readlines()
		
	content = [x.strip() for x in content]

	for line in content:
		parser.parse(line)
else:
	code = "{print(1+2);}"
	ast = yacc.parse(code)
	ast.execute()
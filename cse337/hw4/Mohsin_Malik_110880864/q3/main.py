from Tkinter import *
from tkMessageBox import *

root = Tk()
root.title("CSE 337 Drawing Program")
root.geometry("200x100")
var = IntVar()
var2 = IntVar()
canvas = Canvas(root, borderwidth=2, relief="solid")

def render():
	value = var.get()
	p1 = 0
	p2 = 0
	p3 = 0
	p4 = 0
	try:
		p1 = int(param1.get())
		p2 = int(param2.get())
		p3 = int(param3.get())
		p4 = int(param4.get())
	except ValueError:
		showerror("Error", "Parameters must be numbers")
		return
	width = canvas.winfo_width()
	height = canvas.winfo_height()
	if p1 < 0 or p1 > width or p3 < 0 or p3 > width or p2 < 0 or p2 > height or p4 < 0 or p4 > height:
		showerror("Error", "Object not in range of canvas")
		return
	color = ""
	value2 = var2.get()
	if value2 == 1:
		color = "red"
	elif value2 == 2:
		color = "green"
	elif value2 == 3:
		color = "blue"
	if value == 1:
		canvas.create_rectangle(p1, p2, p3, p4, outline=color)
	elif value == 2:
		canvas.create_oval(p1, p2, p3, p4, outline=color)
	elif value == 3:
		canvas.create_line(p1, p2, p3, p4, fill=color)

param1 = Entry(root)
param2 = Entry(root)
param3 = Entry(root)
param4 = Entry(root)
p1label = Label(root, text="Param1")
p2label = Label(root, text="Param2")
p3label = Label(root, text="Param3")
p4label = Label(root, text="Param4")
rect = Radiobutton(root, text="Rectangle", variable=var, value=1)
oval = Radiobutton(root, text="Oval", variable=var, value=2)
line = Radiobutton(root, text="Line", variable=var, value=3)
red = Radiobutton(root, text="Red", variable=var2, value=1)
green = Radiobutton(root, text="Green", variable=var2, value=2)
blue = Radiobutton(root, text="Blue", variable=var2, value=3)
render = Button(root, text="Render", command=render)

var.set(1)
var2.set(1)

canvas.pack()
p1label.pack()
param1.pack()
p2label.pack()
param2.pack()
p3label.pack()
param3.pack()
p4label.pack()
param4.pack()
rect.pack()
oval.pack()
line.pack()
render.pack()
red.pack()
green.pack()
blue.pack()

root.mainloop()

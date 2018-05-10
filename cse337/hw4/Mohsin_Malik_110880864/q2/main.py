from flask import Flask, render_template, request, redirect, flash, get_flashed_messages

app = Flask(__name__)
app.secret_key = b'_5#y2L"F4Q8z\n\xec]/'

@app.route('/', methods=['GET', 'POST'])
def home():
	if request.method == "POST":
		op = request.form['op']
		fnum = request.form['fnum']
		snum = request.form['snum']
		try:
			if '.' in fnum:
				fnum = float(fnum)
			else:
				fnum = int(fnum)
			if '.' in snum:
				snum = float(snum)
			else:
				snum = int(snum)
		except ValueError:
			return redirect("/result/error")
		result = 0
		if op == "add":
			result = fnum + snum
		elif op == "sub":
			result = fnum - snum
		elif op == "mult":
			result = fnum * snum
		elif op == "divide":
			result = fnum / snum
		flash(result)
		return redirect("/result/" + op)
	return render_template("home.html")

@app.route('/result/<op>')
def op(op):
	if op == "error":
		return render_template("error.html", error="Must be ints or floats.")
	msgs = get_flashed_messages()
	if len(msgs) == 0:
		return render_template("error.html", error="Error occured. Try again.")
	return render_template("result.html", result=msgs[0])

@app.errorhandler(404)
def err_404(e):
	return render_template("err.html", errorcode=404)

@app.errorhandler(403)
def err_403(e):
	return render_template("err.html", errorcode=403)

@app.errorhandler(500)
def err_500(e):
	return render_template("err.html", errorcode=500)

if __name__ == "__main__":
	app.run()

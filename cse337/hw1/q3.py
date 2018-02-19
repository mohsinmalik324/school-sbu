import urllib.request
from bs4 import BeautifulSoup

def get_story_name(story):
	return story.find(class_="name").find("a").get_text()
	
def get_story_link(story):
	return story.find(class_="name").find("a")["href"]
	
def get_story_date(story):
	return story.find(class_="date").get_text()

r = urllib.request.urlopen("https://finance.google.com/finance/market_news")
soup = BeautifulSoup(r, "html.parser")
stories = soup.select("div.g-section.news.sfe-break-bottom-16")

file = open("top10articles.txt", "w")
count = 0

for s in stories:
	if count >= 10:
		break
	count += 1
	title = get_story_name(s)
	link = get_story_link(s)
	date = get_story_date(s)
	line = title + "," + link + "," + date
	if count < 10:
		line += "\n"
	file.write(line)
	
file.close()
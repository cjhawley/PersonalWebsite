from fastapi import FastAPI
from fastapi.staticfiles import StaticFiles
from fastapi.responses import FileResponse

app = FastAPI(docs_url=None, redoc_url=None)
app.mount("/", StaticFiles(directory="resources", html=True), name="static")

@app.get("/")
def index():
    return FileResponse("index.html", media_type="html")



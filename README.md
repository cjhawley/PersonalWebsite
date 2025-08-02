# PersonalWebsite
My personal website. 

## Deploy

Deploy on commit using Github Actions and Google Cloud Run.


## Required Software (for development)

1. Python 

## Installation & Setup

```bash
$ source .venv/bin/activate
$ pip install -r requirements.txt
```

## Running
### Development 
```bash
$ fastapi dev main.py
```

### Production
```bash
uvicorn main:app --host 0.0.0.0 --port 80
```


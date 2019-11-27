# PersonalWebsite
My personal website. 

## Deploy

Deploy using Github Actions and Google Cloud Run.

In order to deploy, a tag must be created and pushed:

```bash
$ git tag -a "..." -m "..."
$ git push origin "..."
```

## Required Software (for development)

1. Docker 
2. Java
3. Maven

## Testing

```bash
$ mvn clean test
```

## Running

### Build

```bash
$ mvn clean package
```

### Run

```bash
java -jar target/personalwebsite.jar
```

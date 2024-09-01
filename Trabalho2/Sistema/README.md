## Integrantes

- Carolina Ferreira
- Felipe Freitas
- Luiza Heller
- Mateus Caçabuena

## Importante

Multi-step Dockerfile não funcionou pela falta do mvnwrapper.jar nas pastas .mvn. Por isso, é necessário criar os arquivos .jar antes de rodar o docker compose. Para isso, utilize o script bash apropriado (package-all.sh ou package-all.bat).

## How to Run

```sh
package-all.bat
```

```sh
docker-compose up --build --scale asscache=3
```

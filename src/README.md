## Python

Pour l'installation des dépendances backend python il faut : 

### Sur Windows : 
```bash
py -m pip install --user pipenv # Permet d'installer pipenv pour la gestion des dépendances
```
- Il faudra impérativement rajouter le dossier %APPDATA%\Python\Python<python_version>\Scripts au PATH de l'utilisateur pour pouvoir utiliser pipenv
### Sur MacOS : 
```bash
pip3 install --user pipenv # Permet d'installer pipenv pour la gestion des dépendances
```



```bash
pipenv run server
```
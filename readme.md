#  Projeto de Compiladores

#### Traduz um script de criação de tabela em model, viewset e serializer para ser usado no Django Rest Framework(DRF).

### Integrantes

- Lucas Hellanio de Melo Silva
- João Vitor de Assis Nogueira
- Pedro Gabriel da Silva Lima.
- Pedro Augusto Leite Figueira Oliveira
- Yves Henry Gouveia Gregório

### Funcionalidades

- [x] Criação da Model
- [x] Criação do Viewset
- [x] Criação do Serializer


### 🎲 Executando o compilador

```bash
# Para limpar arquivos gerados anteriormente
$ make clean

# Para gerar novos arquivos e executar o compilador
$ make run

# Para visualizar o output gerado, basta abrir a pasta output
$ cd output

```


### Exemplo de input

```
CREATE TABLE User (
  Name VARCHAR (100) ,
  Email VARCHAR (100),
  Age INT NOT NULL,
  CPF INT,
  Balance FLOAT NOT NULL,
  Birthdate DATE,
  primary key (CPF)
);


```

### Output esperado

#### models.py
```
from django.db import models

class User(models.Model):
	name = models.CharField()
	email = models.CharField()
	age = models.IntegerField()
	cpf = models.IntegerField(primary_key=True)
	balance = models.FloatField()
	birthdate = models.DateTimeField('birthdate')

```

#### serializers.py
```
from rest_framework.serializers import ModelSerializer

from .models import User

class UserSerializer(ModelSerializer):
	class Meta:
		model = User
		fields = '__all__'
```

#### viewsets.py
```
from rest_framework.decorators import action
from rest_framework.response import Response
from rest_framework import permissions

from .models import User

class UserViewSet(viewsets.ModelViewSet):
	queryset = User.objects.all()
	serializer_class = UserSerializer

```
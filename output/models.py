from django.db import models

class User(models.Model):
	name = models.CharField()
	email = models.CharField()
	age = models.IntegerField()
	cpf = models.IntegerField(primary_key=True)
	balance = models.FloatField()
	birthdate = models.DateTimeField('birthdate')

from rest_framework.decorators import action
from rest_framework.response import Response
from rest_framework import permissions

from .api.serializers import UserSerializer

from .models import User

class UserViewSet(viewsets.ModelViewSet):
	queryset = User.objects.all()
	serializer_class = UserSerializer

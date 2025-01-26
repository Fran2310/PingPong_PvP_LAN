# Juego: Ping Pong con sockets
## Descripción
 Juego en el que entre varios computadores se organiza una partida de ping pong, dónde dos maquinas van a ser los jugadores, que van a poder mover las raquetas; el resto de computadores van a ser una parte del tablero como espectadores.
 
## Requisitos
 - Java JDK 17 o superior 
 - Windows 7 o superior
 - Conexión a internet
 - Desactivar firewall
 
## Instrucciones de uso
- Primero se debe que iniciar el servidor,  para esto se debe que ejecutar el archivo .jar con el parámetro de 'server'

	java -jar .\prog3_pingPongGame.jar server server

- Luego se deberá que iniciar los clientes en cada máquina, para esto se debe que ejecutar el archivo .jar con el parámetro de 'client'

	java -jar .\prog3_pingPongGame.jar server client
     
 - En el menú del cliente, se deberá que ingresar la IP de la máquina del servidor, para saber cuál es la ip, en la máquina donde se encuentra el servidor se puede ejecutar 'ipconfig' en la del equipo.
 - Se da clic al botón de 'Entrar' y queda esperar a que el servidor inicie el juego.
 - Para iniciar el juego, se deberá que contar con mínimo dos jugadores, ya con esto, en la máquina del servidor, se da clic al botón de 'Iniciar'.
 
## Información adicional
- Se puede cambiar distintas características en el archivo properties:
    - tamaño de la bola, raquetas y ventanas.
    - velocidad de la bola y raquetas.
- Los botones para mover las raquetas son las flechas de arriba y abajo.

## Cambios
- Conexión de más de 2 clientes arreglado
- Menu para cambiar el color de la bola

## Autor
- Fabian Leonardo Correa Rojas
- Alejandro Forero Noguera
# Trabajo Práctico Especial - POO - Diciembre 2022

## Tabla de contenidos

* [Autores](#autores)
* [Objetivo](#objetivo)
* [Descripción](#descripción)
* [Funcionamiento](#funcionamiento)
* [Tarea 1 - Personalización de Figuras](#tarea1)
* [Tarea 2 - Copiar, Cortar y Pegar](#tarea2)
* [Tarea 3 - Deshacer y Rehacer](#tarea3)

## Autores

* Pérez de Gracia, Mateo - Legajo: **63401**
* Quian Blanco, Francisco - Legajo: **63006**
* Stanfield, Theo - Legajo: **63403**

## Objetivo

El objetivo del proyecto es la implementación de una aplicación de dibujo de figuras, desarollado en el lenguaje Java, con interfaz visual en JavaFX, aplicando los conceptos sobre la programación orientada a objetos adquiridos en la materia.

## Descripción
El programa consta de una aplicación de dibujo, estilo Paint de Microsoft, el cual permite dibujar distintas figuras. Dentro de estas figuras se permiten dibujar cuadrados, rectángulos, elipses y círculos. También se permite seleccionar las figuras, las cuales al estar seleccionadas cambian el color de su borde al color rojo. Una vez seleccionada una figura esta se puede eliminar.

## Funcionamiento
Para la fácil separación del front-end y el back-end, estos dos se encuentran totalmente separados. Dentro del front-end se encuentra diferentes archivos los cuales se encargarán de todo tipo de acción o experiencia con el usuario, y dentro del back-end se podrá encontrar todo el procesamiento interno que realiza el programa.

#### Front-end
Recorriendo el front-end nos encontramos con todos los archivos tipo java encargados de la composición del “Frame”. Como se puede ver, se utilizó una separación tipo “VBox” para separar el “Frame” en dos, siendo el menú el que se encuentre más elevado de los dos. Ahora sí, pasando a la segunda división, se utilizó una división de “BorderPane” ya que nos resultó la más fácil para situar barras de herramientas al lado izquierdo de la pantalla como también a la parte superior de la misma (el canvas se situa en el lugar restante, situado a la derecha de la pantalla). Esta asignación es muy sencilla debido a las funciones “setTop” y “setLeft" que contiene “BorderPane”.

```java
setTop(topBar);
setLeft(sideBar);
setRight(canvas);
```

La barra de herramientas situada a lado izquierdo de la pantalla es distribuida utilizando una “VBox” para que los elementos quedan alineados uno encima del otro y por último la barra de herramientas situada en la parte superior de la pantalla será distribuida utilizando nuevamente una “VBox” para poder separarla en dos, la parte superior de esta conteniendo los botones de copiar, cortar y pegar, y la parte inferior de esta conteniendo los botones de rehacer y deshacer. Una vez conseguida esta separación se la distribuye con una “HBox” para que los elementos queden asignados uno al lado del otro.

#### Back-end
En el back-end encontramos dos tipos de archivos distintos separados por su funcionalidad.
Por primer plano tenemos todas las clases de las figuras las cuales implementan una interfaz “MovableSketch” la cual es la encargada de permitir a la figura desplazarse por alrededor del canvas. Por otro lado tenemos la clase “CanvasState” cuya funcionalidad es todo procesamiento que ocurra en el canvas.

```java
private final List<FigureRender<? extends Figure>> list = new ArrayList<>();
```

Al tener una lista de las figuras presentes en la pantalla, se vuelven sencillas las actividades como eliminar y agregar distintas figuras, como también copiar el formato de las mismas.

## Tarea 1 - Personalización de Figuras

## Tarea 2 - Copiar, Cortar y Pegar

## Tarea 3 - Deshacer y Rehacer

# Decisiones de Diseño — BiblioTech

## Modelo
- Se usaron interfaces para `Recurso` y `Socio` para que tanto libros como socios puedan tener distintos tipos sin repetir código.
- `LibroFisico`, `Ebook`, `Estudiante` y `Docente` se definieron como `record` porque sus datos no cambian una vez creados.
- `Categoria` es un `enum` para que solo se puedan usar categorías predefinidas y no cualquier texto.
- El límite de libros (3 para Estudiante, 5 para Docente) está definido en cada tipo de socio.

## Repositorios
- Hay una interfaz base `Repository` que comparten todos los repositorios para no repetir los métodos comunes.
- Los datos se guardan en memoria usando un `HashMap`, donde la clave es el ISBN o el DNI según el caso.
- El repositorio de préstamos guarda por separado los préstamos activos y el historial de devoluciones.

## Servicios
- Cada servicio recibe lo que necesita al momento de crearse, en lugar de buscarlo él mismo.
- Las búsquedas devuelven `Optional` para no tener que devolver `null` cuando no se encuentra algo.
- El período de préstamo es de 14 días y está definido en un solo lugar para poder cambiarlo fácilmente.

## Excepciones
- Se creó una excepción base `BibliotecaException` de la cual heredan todas las demás.
- Cada excepción tiene su propio mensaje para que el error sea claro para quien lo lee.

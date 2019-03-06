# boat-tracker

## Structure des objets
### `Containership`
* `id`: `int`
* `name` : `string`
* `captainName`: `string`
* `latitude`: `float`
* `longitude`: `float`
* `port`: `Port`
* `type`: `ContainershipType`
* `Containers`: `Container[]`
### `Container`
* `id`: `int`
* `length`: `int`
* `heigth`: `int`
* `width`: `int`
### `ContainershipContainer`
* `containershipId`: `int`
* `containerId`: `int`
### `ContainershipType`
* `id`: `int`
* `length`: `int`
* `heigth`: `int`
* `width`: `int`
### `Port`
* `id`: `int`
* `name`: `string`
* `latitude`: `float`
* `longitude`: `float`

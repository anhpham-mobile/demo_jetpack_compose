# 1. Lifecycle

In Jetpack Compose, a composable function goes through three main phases:

- **Enter the Composition**

- **Recomposition**

- **Leave the Composition**

![](https://developer.android.com/static/develop/ui/compose/images/lifecycle-composition.png?hl=vi)


# 2. Stateless and Stateful

- **Stateless Composables**: :contentReference[oaicite:5]{index=5}&#8203;:contentReference[oaicite:6]{index=6}

  *Example:*

  
```kotlin
  @Composable
  fun Greeting(name: String) {
      Text(text = "Hello, $name!")
  }
```

# 3. State Management in Jetpack Compose

Managing state effectively is crucial in Jetpack Compose to ensure that your UI responds correctly to user interactions and data changes. Two primary functions used for state management are `remember` and `rememberSaveable`.

## `remember`

The `remember` function is used to store a single object in memory during recompositions. It ensures that the state is retained as long as the composable is part of the composition. However, the state is not retained across configuration changes like screen rotations.

*Example:*

```kotlin
@Composable
fun Counter() {
    var count by remember { mutableStateOf(0) }
    Column {
        Text(text = "Count: $count")
        Button(onClick = { count++ }) {
            Text("Increment")
        }
    }
}
```

## `rememberSaveable`

To preserve state across configuration changes, such as device rotations, Jetpack Compose provides the `rememberSaveable` function. This function works similarly to `remember` but utilizes the `SavedStateHandle` to save and restore state automatically during such events.​

*Example:*

```kotlin
@Composable
fun Counter() {
    var count by rememberSaveable { mutableStateOf(0) }
    Column {
        Text(text = "Count: $count")
        Button(onClick = { count++ }) {
            Text("Increment")
        }
    }
}
```

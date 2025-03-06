# 1. Lifecycle

In Jetpack Compose, a composable function goes through three main phases:

- **Enter the Composition**

- **Recomposition**

- **Leave the Composition**

![](https://developer.android.com/static/develop/ui/compose/images/lifecycle-composition.png?hl=vi)


# 2. Stateless and Stateful

- **Stateless Composables**:

  *Example:*

  
```kotlin
  @Composable
  fun Greeting(name: String) {
      Text(text = "Hello, $name!")
  }
```

- **Stateful Composables**:

  *Example:*

  
```kotlin
  @Composable
  fun Counter() {
      var count by remember { mutableStateOf(0) }
      Button(onClick = { count++ }) {
          Text("Count: $count")
      }
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

# 4. State Hoisting

State hoisting is a design pattern in Jetpack Compose that involves moving state up to a common ancestor composable, making child composables stateless. This approach enhances reusability and testability, and ensures a single source of truth for state management.

### Composable Parent Should Manage State

*Example:*

```kotlin
@Composable
fun Parent() {
    var text by remember { mutableStateOf("") }
    Child(
        text = text,
        onTextChange = { newText -> text = newText }
    )
}
```
### Composable Child Should Get Data from Parent and Push Events to Parent

*Example:*

```kotlin
@Composable
fun Child(text: String, onTextChange: (String) -> Unit) {
    TextField(
        value = text,
        onValueChange = onTextChange
    )
}
```

### Reusability & Testability

# 5. Manage State in Jetpack Compose

Effective state management is crucial in Jetpack Compose to build responsive and maintainable user interfaces. There are several approaches to managing state, each suitable for different scenarios.

### In-Composable State Management
- `remember`
- `rememberSaveable`

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

### State Holder: Creating a Class to Manage State
For more complex state management, especially when dealing with multiple state variables or intricate UI logic, encapsulating state within a separate class—often referred to as a state holder—is beneficial. This approach promotes separation of concerns and enhances testability

*Example:*

```kotlin
class CounterState {
    var count by mutableStateOf(0)
        private set

    fun increment() {
        count++
    }
}

@Composable
fun Counter(counterState: CounterState = CounterState()) {
    Column {
        Text(text = "Count: ${counterState.count}")
        Button(onClick = { counterState.increment() }) {
            Text("Increment")
        }
    }
}
```

### ViewModel: Using in MVVM Architecture
In the Model-View-ViewModel (MVVM) architecture, the ViewModel class from Android's Architecture Components is utilized to manage UI-related data in a lifecycle-aware manner. ViewModel instances survive configuration changes, making them ideal for managing state that needs to persist across such events.
*Example:*

```kotlin
class CounterViewModel : ViewModel() {
    private val _count = MutableStateFlow(0)
    val count: StateFlow<Int> = _count.asStateFlow()

    fun increment() {
        _count.value++
    }
}

@Composable
fun Counter(viewModel: CounterViewModel = viewModel()) {
    val count by viewModel.count.collectAsState()
    Column {
        Text(text = "Count: $count")
        Button(onClick = { viewModel.increment() }) {
            Text("Increment")
        }
    }
}
```

###Choosing the appropriate state management approach depends on the complexity and scope of the state:

- `In-Composable State`: Suitable for simple, ephemeral state confined to a specific composable.

- `State Holder`: Ideal for managing more complex state or when multiple composables need to share state without involving the broader app architecture.

- `ViewModel`: Best for app-wide state management, especially when state needs to persist across configuration changes and align with the MVVM architecture.

# Android VIP Boilerplate
The repository is the boilerplate source code written in [Kotlin](https://kotlinlang.org/) programming language.

## Architecture
The application is built using [clean-swift](https://clean-swift.com/) architecture. The architecture is modified from the original Uncle Bob's clean architecture. The source code contains different layers of VIPER architecture which is demonstrated through the movie app.
The flow of this architecture is unidirectional in following manner-
`View -> Interactor -> Presenter -> View`

For more details of the clean-swift architecture, please read [this blog](https://rubygarage.org/blog/clean-swift-pros-and-cons).

Each layer of VIP architecture and data flow are described below-

## **View**
The view starts and ends the VIP cycle. Here `View` is an interface which contains all data methods to show data in the view.
The aim of view is to request data from interactors and show it to UI. It gets data from presenter but cannot send anything to the presenter. It has unidirectional interaction from Interactor and Presenter both.

```kotlin
interface View : BaseView {
    fun showMovies(viewModel: MovieModels.ViewModel)
    fun showError(viewModel: ErrorViewModel)
}
 ```
 
In Android, Activity or Fragment implements the View. Below is the example of `MovieListActivity`-

```kotlin
class MovieListActivity : BaseActivity(), MovieListContract.View, OnMovieItemClickedListener {
    internal lateinit var interactor: MovieListContract.Interactor
    internal lateinit var router: MovieListContract.Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)
        init()
    }

    private fun init() {
        MovieListConfigurator().configure(this)
        interactor.loadMovies()
    }

    private fun initRecyclerView(movies: List<MovieModels.MovieViewModel>) {
        ...
    }

    override fun showMovies(viewModel: MovieModels.ViewModel) {
        initRecyclerView(viewModel.movies)
    }

    override fun showError(viewModel: ErrorViewModel) {
        ToastUtils.showLongToast(context, viewModel.errorMessage)
    }

    override fun onMovieItemClicked(movie: MovieModels.MovieViewModel) {
        router.goToMovieDetails(movie)
    }

    override fun onDestroy() {
        interactor.clear()
        super.onDestroy()
    }
}
```

The activity contains an instance of router and interactor. Also, this is the place where we will configure everything using configurator.

## Interactor-
Interactors are also called Use Cases. This is the place to write core business logic of application which can be tested independently without the dependency on platform/framework specific things i.e UI, Database. The job of an interactor is to get query data from view and send the response data to the presenter.

```kotlin
class MovieListInteractor(
    private val presenter: MovieListContract.Presenter,
    private val repository: MovieListContract.Repository
) : MovieListContract.Interactor {

    private val disposableCollector = DisposableCollector()

    override fun loadMovies() {
        val moviesObservable = repository.loadMovies()
        val stepSubscribe = moviesObservable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> handleLoadMoviesSuccess(result) },
                { throwable -> handleLoadMoviesFailure(throwable) }
            )
        disposableCollector.addDisposable(stepSubscribe)
    }

    private fun handleLoadMoviesSuccess(movies: List<MovieModels.Movie>) {
        presenter.presentMovies(movies)
    }

    private fun handleLoadMoviesFailure(throwable: Throwable) {
        presenter.presentError(throwable.message)
    }

    override fun clear() {
        disposableCollector.clearDisposables()
    }
}
```
Here you can see that interactor is containing instance of `MovieListContract.Presenter` and `MovieListContract.Repository` which are explained later.

## Presenter-
The Presenter is responsible for presentation logic. It decides how data will be presented to the user. The Presenter organizes the business model/data sent by the Interactor into ViewModels which can be displayed in the UI without using any logic.

```kotlin
class MovieListPresenter(private val view: MovieListContract.View) : MovieListContract.Presenter {
    override fun presentMovies(movies: List<MovieModels.Movie>) {
        val isError = movies.isEmpty()
        if (isError) {
            val viewModel =
                ErrorViewModel(view.getResourcesInstance().getString(R.string.no_movie_found))
            view.showError(viewModel)
        } else {
            val movieViewModels =
                movies.map { MovieModels.MovieViewModel(it.id, it.title, it.posterPath, it.overview, false) }
            val instruction = view.getResourcesInstance().getString(R.string.tap_to_view_details)
            val viewModel = MovieModels.ViewModel(instruction, movieViewModels)
            view.showMovies(viewModel)
        }
    }

    override fun presentError(message: String?) {
        val viewModel =
            ErrorViewModel(message ?: view.getResourcesInstance().getString(R.string.unexpected_error_occurred))
        view.showError(viewModel)
    }
}
```

## Models-

There are models for different layers i.e. a business model, view model.

Entities are enterprise-wide business rules that encapsulate the most general business rules and also contain Data Transfer Objects (DTOs). When external changes, these rules are the least likely to change.

They are not only models that do not move, but they are also rules that make the hearth of your business. In a way, Entities define clearly the intent of your business. For example, an app about booking a movie(a movie ticket booking app) should have an entity called `Movie` and another entity called `Ticket`. Because the core of this app is "user books a movie ticket".

```kotlin
class MovieModels {
    data class Movie(
        val releaseDate: String? = null,
        val overview: String? = null,
        val adult: Boolean = false,
        val originalTitle: String? = null,
        val posterPath: String? = null,
        val popularity: Double = 0.toDouble(),
        val title: String? = null,
        val voteAverage: Double = 0.toDouble(),
        val id: Int = 0,
        val voteCount: Int = 0
    )

    data class ViewModel(
        val instruction: String,
        val movies: List<MovieViewModel>
    )

    @Parcelize
    data class MovieViewModel(
        val id: Int?,
        val title: String?,
        val posterPath: String?,
        val overview: String?,
        val isSelected: Boolean
    ) : Parcelable

    data class Request(
        val searchKeyword: String
    )
}
```

## Router-
The router handles the navigation between screens. A person can tap on one of the movies from movie list and navigates to the details page of the movie.

```kotlin
class MovieListRouter(private val activity: MovieListActivity) : MovieListContract.Router {
    override fun goToMovieDetails(movie: MovieModels.MovieViewModel) {
        val intent = Intent(activity, MovieDetailsActivity::class.java)
        intent.putExtra(Constants.IntentExtras.MOVIE, movie)
        activity.startActivity(intent)
    }
}
```


# Configurator-
Configurator is a class that wires everything. It initializes all layers of VIPER.
```kotlin
class MovieListConfigurator : Configurator<MovieListActivity> {
    override fun configure(view: MovieListActivity) {
        val presenter = MovieListPresenter(view)
        val repository = MovieListRepository(ApiClient.movieApiService)
        val interactor = MovieListInteractor(presenter, repository)
        val router = MovieListRouter(view)
        view.interactor = interactor
        view.router = router
    }
}
```

# Data Layer-
We have used the repository to provide data for each feature. `MovieRepository` provides all movie related data. It contains data stores which provide data from REST API or database.

```kotlin
class MovieListRepository(private val movieApiService: MovieApiService) : MovieListContract.Repository {
    override fun loadMovies(): Observable<List<MovieModels.Movie>> {
        val mapper = MovieResponseEntityMapper()
        val movieResponse = movieApiService.fetchMovies()
        return movieResponse.map(mapper::mapFrom)
    }
}
```
Here you can checks if data is present in local storage or when to fetch data from remote data store. Interactor doesn't know where data is coming.

## Contract
Contract contains all interfaces of a module. By seeing at the contract, one can describe the behaviour of the system. Each major component of the application implements interface. One example of movie list contract is as follows:
```kotlin
class MovieListContract {
    interface View : BaseView {
        fun showMovies(viewModel: MovieModels.ViewModel)
        fun showError(viewModel: ErrorViewModel)
    }

    interface Router {
        fun goToMovieDetails(movie: MovieModels.MovieViewModel)
    }

    interface Presenter {
        fun presentMovies(movies: List<MovieModels.Movie>)
        fun presentError(message: String?)
    }

    interface Interactor {
        fun loadMovies()
        fun searchMovie(request: MovieModels.Request)
        fun clear()
    }

    interface Repository {
        fun loadMovies(): Observable<List<MovieModels.Movie>>
    }
}
```

## Mappers
Mappers are used to convert entity of one layer to another layer.
For example, REST API is returning list of MovieData objects which will be modified in domain layer to perform some operations on it. Again it will be modified to show in the UI.
Following is the one mapper class which converts `MovieResponse` to `MovieModels.Movie`-

```java
class MovieResponseEntityMapper : Mapper<MovieResponse, List<MovieModels.Movie>?> {
    override fun mapFrom(from: MovieResponse): List<MovieModels.Movie>? {
        val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w342";
        val movies = from.movies?.map {
            MovieModels.Movie(
                it.releaseDate,
                it.overview,
                it.adult,
                it.originalTitle,
                POSTER_BASE_URL + it.posterPath,
                it.popularity,
                it.title,
                it.voteAverage,
                it.id,
                it.voteCount
            )
        }
        return movies
    }
}
```


# Unit Testing
Project contains unit test cases of different layers. Following are some examples of test cases-

## Mappers
Following is the example of movie mapper's unit test case which tests whether values are mapped from network layer model to the business model as expected or not.

```kotlin
class MovieMapperTest {

    @Test
    fun testMoviesMapper_valuesShouldBeMappedAsExpected() {
        val moviesData = MovieTestUtils.getDummyMoviesData(3)
        val movieResponse = MovieResponse(
            page = 0, totalResults = moviesData.size,
            totalPages = 2, movies = moviesData
        )
        val moviesMapper = MovieResponseEntityMapper()
        val movies = moviesMapper.mapFrom(movieResponse)
        for (i in 0 until moviesData.size) {
            val movie = movies!!.get(i)
            val movieData = moviesData[i]
            Assert.assertEquals(movie.id, movieData.id)
            Assert.assertEquals(movie.title, movieData.title)
            Assert.assertEquals(movie.overview, movieData.overview)
            Assert.assertEquals(movie.posterPath, MovieTestUtils.POSTER_BASE_URL + movieData.posterPath)
        }
    }
}
```

## Interactors
Interactor is the most important layer which contains core business logic, so it should be tested properly. Each case must be covered i.e. success state and different error states. We will mock other layers using Mockito library. Following is the example of unit test cases of movies interactor-

```kotlin
class MovieUnitTest {
    @Rule
    @JvmField
    val immediateSchedulerRule = RxImmediateSchedulerRule()
    private lateinit var interactor: MovieListContract.Interactor
    private lateinit var presenter: MovieListContract.Presenter
    private lateinit var repository: MovieListContract.Repository

    @Before
    fun setup() {
        repository = Mockito.mock(MovieListContract.Repository::class.java)
        presenter = Mockito.mock(MovieListContract.Presenter::class.java)
        interactor = MovieListInteractor(presenter, repository)
    }

    @Test
    fun testLoadMovies_moviesShouldBePresented() {
        val listMovies = MovieTestUtils.getDummyMovies(5)
        `when`(repository.loadMovies()).thenReturn(Observable.just(listMovies))
        interactor.loadMovies()
        verify(presenter).presentMovies(listMovies)
    }

    @Test
    fun testLoadMovies_errorShouldBePresented() {
        val throwable = Throwable("Unexpected error occurred")
        `when`(repository.loadMovies()).thenReturn(Observable.error<List<MovieModels.Movie>>(throwable))
        interactor.loadMovies()
        verify(presenter).presentError(ErrorHandler.getError(throwable))
    }
}
```

# Package Structure
Following are the package in app:

1. **scene -** It contains packages of all modules. Sub-package name would be the name of module i.e. movie which contains all class of this module i.e. Contract, Presenter, Interactor, Models, Activity, Adapter etc. 
2. **customview -** It contains children of Android `View` or `ViewGroup` to extend the functionality of existing views i.e. `CustomTextView` 
3. **network -** It contains classes of retrofit framework and request-response models.
4. **common -** It contains common classes which will be used in almost every module i.e. BaseView, Constants etc.
5. **utils -** It contains helper classes i.e. NetworkUtils, GlideUtils etc.


# Technologies
1. Kotlin language
1. RxAndroid
3. Retrofit
4. SDP library
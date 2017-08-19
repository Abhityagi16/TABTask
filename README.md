# TABTask

This app fetches 100 comics from the Marvel API.

I couldn't add offline support because of time contraints. I've set it up all that is needed is
to create a local database.

I've used MVP pattern for this task.

Libraries Used: RxJava, Retrofit, Picasso

If I had more time I would've also added Dagger 2 for injecting Presenter and repositories. I
also would move all string resources to strings.xml.

I've not created any Presenter for the Detail screen as it's completely static. There is no user
interation in that screen.
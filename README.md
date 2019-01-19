# MyUdacityProjects-Android-BakingApp-V2

### Project Overview
You will productionize an app, taking it from a functional state to a production-ready state. This will involve finding and handling error cases, adding accessibility features, allowing for localization, adding a widget, and adding a library.

### Why this Project?
As a working Android developer, you often have to create and implement apps where you are responsible for designing and planning the steps you need to take to create a production-ready app. Unlike Popular Movies where we gave you an implementation guide, it will be up to you to figure things out for the Baking App.

### What Will I Learn?
In this project you will:

* Use MediaPlayer/Exoplayer to display videos.
* Handle error cases in Android.
* Add a widget to your app experience.
* Leverage a third-party library in your app.
* Use Fragments to create a responsive design that works on phones and tablets.

#### App Description
Your task is to create a Android Baking App that will allow Udacity’s resident baker-in-chief, Miriam, to share her recipes with the world. You will create an app that will allow a user to select a recipe and see video-guided steps for how to complete it.

The recipe listing is located here.

The JSON file contains the recipes' instructions, ingredients, videos and images you will need to complete this project. Don’t assume that all steps of the recipe have a video. Some may have a video, an image, or no visual media at all.

One of the skills you will demonstrate in this project is how to handle unexpected input in your data -- professional developers often cannot expect polished JSON data when building an app.

Click "Next" to see some sample mocks for the app!


####  My notes to reviewer and anyone else looking

Due to real-world issues and submission deadlines, this isn't a 100% perfect example of best work.  If you want better examples, I suggest looking at my followed developers.

## There are problems as a result of some Android Studio weirdness!!!

I literally watched a an icon jump from a v24 to an xxhdpi to the drawable roots by doing gradle syncs.  I have had times where the app will completely ignore breakpoints and times where it will detach from the IDE and continue to work, and times where the app unexpectedly crash for no reason without user input.  Most likely it was the result of running in Ubuntu 18.10, but due to submission deadlines I will address that after project passing. 

## Libraries used:

* Google / Standard

  * Gson for serialization - I personally prefer this for moving POJOs to things like Moshi - just a personal thing
  
  * The usual layouts etc.  I do call Exifinterface specifically even though I don't use it, that is because Picasso calls an older version which causes gradle heartburn.

  * Volley for network calls.

* Icepick for persistence.

* Exoplayer for videos.

* Picasso for pictures.

# Other Attribution

I did have help, including from my brother and reviewing other projects.  All of the code in here was typed and changed and done by me or from using the Android templates (Which I do not feel counts as plagiarism since this is an education, not commercial, project.).  When looking to examples I only used and transformed the parts I was comfortable with or understood the what and why.

I had a few other libraries for beautifying recycler usage etc, but eventually stripped them out because of other Android Studio issues I was blaming on the libraries.




## What did I learn?

I feel I really got a good feeling of both fragments and testing.  I am already salivating at the possibilites that can occur from testing.  I also am confident I can use widgets wisely and effectively - although they are MUCH scarier than I originally though.


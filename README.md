# Student Timetable Parser
###### Computer Science • Babeș-Bolyai University Cluj-Napoca

This is an **unofficial** parser for the **official** timetable located on [the faculty's website](https://www.cs.ubbcluj.ro/).

## Why does this open-source project exist?
Because computer science students love to:
- Play around with **code**
- Include **real-life data** in their projects
- Have a timetable that **updates automatically**
- **De-clutter** their everyday life
- Store important data **offline**

## What does it _actually_ do?
It takes **all** timetables available on [the faculty's website](https://www.cs.ubbcluj.ro/) then fixes some formatting, encoding and spelling errors 
and finally saves it in a neat format, as [YAML](https://en.wikipedia.org/wiki/YAML) and [Markdown](https://en.wikipedia.org/wiki/Markdown) files.  

The best part is that the code runs every day at 2 AM using [Github Actions](https://github.com/features/actions) and saves 
the timetables [here](https://horatiu-udrea.github.io/cs-ubb-timetable-parser/) using [Github Pages](https://pages.github.com/), so they are always up-to-date.

## How can I use this?
**Get creative!**  

Here are some ideas:
- Check your timetable - simplest thing you can do
- Create **your own** timetable application that downloads and uses the parsed files - my personal favorite
- Use the code as an example to learn **Kotlin** - nothing beats a real and simple use case
- Create your own Kotlin application and embed the code directly - nobody's stopping you
- Contribute to the code - help people and learn stuff at the same time

## How can I download the timetable files?
You can navigate to the index [here](https://horatiu-udrea.github.io/cs-ubb-timetable-parser/), choose a timetable and follow the link for the YAML format. 

You can wither download it using CTRL+S (Windows/Linux) or CMD+S (MacOS) if you are in the browser, or use the link with your favorite programming language and YAML parser to do anything you want with the data. 
The timetables should be up-to-date, unless something broke in the meantime.

To download all timetables manually from your local machine, open a terminal in a directory where you want to download the project, and then run these commands
```bash
git clone https://github.com/horatiu-udrea/cs-ubb-timetable-parser.git
cd cs-ubb-timetable-parser
./gradlew run
```
If no errors occur, the timetable files will be downloaded in the `timetables` directory, located inside the project directory.

## Cool project! How can I help?
1. ⭐ Star this repository. It's easy to do and it lets everybody know that it's worth maintaining.
2. 💬 Join the [Timetable App's Telegram channel](https://t.me/+yddqH8xAGkIyZGI8) to get all news!
3. 🔀 Create a [Pull Request](https://docs.github.com/en/pull-requests/collaborating-with-pull-requests/proposing-changes-to-your-work-with-pull-requests/creating-a-pull-request) to improve something!
4. 🥥 [Donate a coconut](https://www.buymeacoffee.com/horatiu.udrea) to the creator of this project.

## Who started all this?
Hi, my name is **Horațiu Udrea**, I'm a passionate **Life Coach** and **Software Engineer**. I love creating **synergistic tools** for people and empowering them to live the **best life** they can, **no compromises**.

Find out more about me on [my website](https://horatiu-udrea.ro) and contact me if you like what you see. I'd love to hear from you!

## What's the story behind the project?
I was a student at this university a while ago, and I wanted to make something useful while I learned Kotlin. 
So I created an **Android application** that parsed the faculty's timetable and showed me only what I needed to see (my group's timetable), how I needed to see it (neatly arranged, only the courses of the current week) 
and when I needed to see it (even offline). 

I wanted to share it with my colleagues and so I published it on Google Play. It quickly exploded in popularity and many students started using it. 

The numbers grew larger and larger over the years, even after I finished my studies.
Unfortunately, the system had a major flaw: since the parser was inside the app's code, whenever the timetable format changed on the website, a quick update was needed to get it working again. 

The "quick update" process was this:
1. Me realising that the app is broken after several emails from students that still used it
2. Trying to see what changed in the new timetable format
3. Creating an update at 12 AM in the night
4. Waiting for Google Play to review and publish it
5. Receiving more emails from students that couldn't see their timetable
6. The update being published after 1 to 10 days
7. The students updating the app after a few days

And all this happened when the timetable was needed most: at the beginning of the semester, when a new timetable was available. 

That's the reason this new project is a building block for systems that won't have these flaws by design. You live, you learn!

## Will this project be maintained?
That depends on you :D

I plan on having a look at the code from time to time, reviewing and merging pull requests and answering some questions. But the heavy lifting will be done by the community. 
And that includes you! You can always step in and improve what you feel needs improving. You can be the 12AM hero or just fix some spelling issue for your colleagues. Everything is appreciated!

If, one day, the direction of the project doesn't suit your style anymore or I simply go silent, the repository can simply be forked and adjusted. No gatekeeping!

## Will you make a new app?
I'd love to. 

The amount of work will depend on the amount of :
- ⭐ Stars this repository gets
- 💬 Subscribers of the [Timetable App's Telegram channel](https://t.me/+yddqH8xAGkIyZGI8)
- 🥥 [Coconuts donated](https://www.buymeacoffee.com/horatiu.udrea)
  
So if this project interests you, make yourself heard!

import com.mntwinsnation.syndication.Channel

class BootStrap {

    def init = { servletContext ->

        new Channel(
                title: 'A Fan\'s View',
                description: """Howard Sinker used to cover the Twins for the Star Tribune, now he maintains a Twins blog with the paper and provides sports commentary on Minnesota Public Radio.

A Fan's View doesn't often delve too far into the stats, but provides vocal opinion and entertaining commentary at a steady pace.""",
                webUrl: 'http://www.startribune.com/blogs/A_Fans_View.html',
                feedUrl: 'http://www.startribune.com/rss/?did=78693312',
                active: true,
                urlKey: 'a-fans-view'
        ).save()

    }
    def destroy = {
    }
}

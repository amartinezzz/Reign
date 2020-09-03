package com.amartinez.reign.domain.model

class HighlightResult (
    var author: Author? = Author(),
    var commentText: CommentText? = CommentText(),
    var storyTitle: StoryTitle? = StoryTitle()
)
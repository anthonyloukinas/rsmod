package gg.rsmod.plugins.content.areas.lumbridge.chat

on_npc_option(npc = Npcs.COOK_4626, option = "talk-to") {
    System.out.println(this.player.getVarp(29))
    when(this.player.getVarp(29)){
        0 -> {
            player.queue { firstDialog(this) }
        }
        1 -> {
            player.queue{ secondDialog(this) }
        }
        else -> {
            player.queue { firstDialog(this) }
        }

    }
}

suspend fun firstDialog(it: QueueTask) {
    it.chatNpc("What am I to do?")
    when(it.options(
            "What's wrong?",
            "Can you make me a cake",
            "You don't look very happy.",
            "Nice hat!"
    )) {
        1 -> {
            it.chatPlayer("What's wrong?")
            finalDialog(it)
        }
        2 -> {
            it.chatPlayer("You're a cook, why don't you bake me a cake")
            it.chatNpc("*sniff* Don't talk to me about cakes...")
            it.chatPlayer("What's wrong?")
            finalDialog(it)
        }
        3 -> {
            it.chatPlayer("You don't look very happy.")
            it.chatNpc("No, I'm not. The world is caving in around me - I am overcome by dark feelings of impending doom.")
            when(it.options(
                    "What's wrong?",
                    "I'd take the rest of the day off if I were you."
            )) {
                1-> {
                    it.chatPlayer("What's wrong?")
                    finalDialog(it)
                }
                2 -> {
                    it.chatPlayer("I'd take the rest of the day off if I were you.")
                    it.chatNpc("No, that's the worst thing I could do. I'd get in terrible trouble.")
                    it.chatPlayer("Well maybe you need to take a holiday...")
                    it.chatNpc("That would be nice, but the Duke doesn't allow holidays for core staff.")
                    it.chatPlayer("Hmm, why not run away to the sea and start a new life as a Pirate?")
                    it.chatNpc("My wife gets sea sick, and I have an irrational fear of eyepatches. I don't see it working myself.")
                    it.chatPlayer("I'm afraid I've run out of ideas.")
                    it.chatNpc("I know I'm doomed.")
                    it.chatPlayer("What's wrong?")
                    finalDialog(it)
                }
            }
        }
        4 -> {
            it.chatPlayer("Nice hat!")
            it.chatNpc("Err thank you. It's a pretty ordinary cooks hat really.")
            it.chatPlayer("Still, suits you. The trousers are pretty special too.")
            it.chatNpc("Its all standard cook's issue uniform...")
            it.chatPlayer("The whole hat, apron, stripey trousers ensemble - it works. It make you looks like a real cook.")
            it.chatNpc("I am a real cook! I have't got time to be chatting about Culinary Fashion. I am in desperate need of help!")
            it.chatPlayer("What's wrong?")
            finalDialog(it)
        }
    }
}

suspend fun finalDialog(it: QueueTask) {
    it.chatNpc("Oh dear, oh dear, I'm in a terrible terrible mess! It's the Duke's birthday today, and I should be making him a lovely big birthday cake.")
    it.chatNpc("I've forgotten to buy the ingredients. I'll never get them in time now. He'll sack me! What will I do? I have four children and a goat to look after. Would you help me? Please?")
    when(it.options(
            "I'm always happy to help a cook in distress.",
            "I can't right now, Maybe later."
    )){
        1 -> {
            it.chatPlayer("Yes, I'll help you.")
            it.player.setVarp(29, 1)
            it.chatNpc("Oh thank you, thank you. I need milk, an egg, and flour. I'd be very grateful if you can get them for me.")
            findIngredients(it)
        }
        2 -> {
            it.chatPlayer("No, I don't feel like it. Maybe later.")
            it.chatNpc("Fine. I always knew you Adventurer types were callous beasts. Go on your merry way!")
        }
    }
}

suspend fun findIngredients(it: QueueTask) {
    it.chatPlayer("So where do I find these ingredients then?")
    when(it.options(
            "Where do I find some flour?",
            "How about milk?",
            "And eggs? Where are they found?",
            "Actually, I know where to find this stuff."
    )) {
        1 ->{
            it.chatNpc("There is a Mill fairly close, go North and then West. Mill lane Mill is just off the road to Draynor. I usually get my flour from there.")
            it.chatNpc("Talk to Millie, she'll help, she's a lovely girl and a fine Miller..")
        }
        2 -> {
            it.chatNpc("There is a cattle field on the other side of the river, just across the road from the Groats' Farm.")
            it.chatNpc("Talk to Gillie Groats, she looks after the Dairy cows - she'll tell you everything you need to know about milking cows!")
        }
        3 -> {
            it.chatNpc("I normally get my eggs from the Groats' farm, on the other side of the river.")
            it.chatNpc("But any chicken should lay eggs.")
        }
        4 -> {
            it.chatPlayer("I've got all the information I need. Thanks.")
        }
    }
}

suspend fun secondDialog(it: QueueTask) {
    it.chatNpc("How are you getting on with finding the ingredients?")
    // TODO: When you have 1/1/1 ingredients, handle dialog
    it.chatPlayer("I haven't got any of them yet, I'm still looking.")
    it.chatNpc("Please get the ingredients quickly. I'm running out of time! The duke will throw me into the streets!")
    it.player.message(
        """
        You still need to get:
        A bucket of milk. A pot of flour. An egg.
        """, ChatMessageType.GAME_MESSAGE)
    when(it.options(
            "I'll get right on it.",
            "Can you remind me how to find these things again?"
    )) {
        1 -> {
            it.chatPlayer("I'll get right on it.")
        }
        2 -> {
            findIngredients(it)
        }
    }
}

// it.chatPlayer("Here's a bucket of mlik.")
// it.chetNpc("Thanks for ingredients you have got so far, please get the rest quickl.y I'm running out of time! The duke will throw me into the streets!")
// You still need to get: An egg
// it.chatPlayer("Here's a fresh egg.")
// it.chatNpc("You've brought me everything I need! I am saved! Thank you!")
// it.chatPlayer("So do I get to go to the Duke's Party?")
// it.chatNpc("I'm afraid not, only the big cheeses get to dine with the Duke.")
// it.chatPlayer("Well, maybe one day I'll be important enough to sit on the Duke's table.")
// it.chatNpc("Maybe, but I won't be holding my breath.")
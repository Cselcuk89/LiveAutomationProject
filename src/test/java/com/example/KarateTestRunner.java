package com.example;

import com.intuit.karate.junit5.Karate;

class KarateTestRunner {

    @Karate.Test
    Karate testAll() {
        return Karate.run().relativeTo(getClass());
    }

    // Example of running a specific feature file
    // @Karate.Test
    // Karate testSample() {
    //     return Karate.run("sample").relativeTo(getClass());
    // }

    // Example of running scenarios with a specific tag
    // @Karate.Test
    // Karate testTags() {
    //     return Karate.run().tags("@yourtag").relativeTo(getClass());
    // }
}

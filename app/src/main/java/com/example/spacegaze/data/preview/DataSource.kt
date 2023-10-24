package com.example.spacegaze.data.preview

import com.example.spacegaze.model.*

class DataSource () {
    fun getMockLaunchData() : Launch {
        return Launch(
            id = "bb327f29-b9c0-4b07-98a5-0493ab351875",
            name = "Falcon 9 Block 5 | Starlink Group 3-5",
            status = LaunchStatus(
                name = "Success",
                abbrev = "S",
                description = "The launch was successful"
            ),
            net = "2023-05-01T03:00:00Z",
            lsp = LaunchServiceProvider(
                name = "SpaceX",
                type = "Commercial"
            ),
            rocket = Rocket(
                configuration = RocketConfiguration(
                    name = "Falcon 9",
                    family = "Falcon",
                    fullName = "Falcon 9 Block 5"
                )
            ),
            mission = Mission(
                name = "Starlink Group 3-5",
                description = "A batch of satellites for the Starlink mega-constellation - SpaceX's project for space-based Internet communication system."
            ),
            pad = Pad(
                name = "Space Launch Complex 40",
                mapUrl = "https://www.google.com/maps?q=28.5623,-80.5774",
                location = Location(
                    name = "Cape Canaveral",
                )
            ),
            image = "https://spacelaunchnow-prod-east.nyc3.digitaloceanspaces.com/media/launch_images/falcon2520925_image_20230209214919.png",
        )
    }
}
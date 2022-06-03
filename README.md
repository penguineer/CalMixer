# Calendar Mixer (CalMixer)

> Load two or more calendards and mix them into a single CalDAV source.

Some tools can only process a single calendar and are unusable when the 
appointment landscape is determined by multiple calendar sources. This
tool will dynamically load these calendars and convert them into a single,
up tp date source that can be used for further processing.

## Deployment

### with Docker

Run with Docker:
```bash
docker run --rm -it mrtux/cal-mixer:latest
```

### Development

This project needs [Maven](https://maven.apache.org/) (at least version 3.2.0) for build and dependency management.

Version numbers are determined with [jgitver](https://jgitver.github.io/). 
Please check your [IDE settings](https://jgitver.github.io/#_ides_usage) to avoid problems, as there are still some unresolved issues.
If you encounter a project version `0` there is an issue with the jgitver generator.

Images are built with a [Dockerfile](Dockerfile) instead of [JIB](https://github.com/GoogleContainerTools/jib). 
I spent 5h trying to bring the build requirements (`latest` only on master, multiple architectures, SemVer considerations) 
and JIB together and then decided it is not  worth the effort. 
In the end it boiled down to the problem that JIB wants "pure" tag names (only the string after `:` while other tools 
provide complete image names). 
Until this is fixed the project sticks to a Dockerfile.

## Maintainers

* Stefan Haun ([@penguineer](https://github.com/penguineer))


## Contributing

PRs are welcome!

If possible, please stick to the following guidelines:

* Keep PRs reasonably small and their scope limited to a feature or module within the code.
* If a large change is planned, it is best to open a feature request issue first, then link subsequent PRs to this issue, so that the PRs move the code towards the intended feature.


## License

[MIT](LICENSE.txt) © 2022 Stefan Haun and contributors

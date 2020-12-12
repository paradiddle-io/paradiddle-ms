# Paradiddle MS

Paradiddle MS is a lightweight, 0-dependency microservices framework with a comprehensible codebase.
This project is loosely inspired by msf4j, but like so many Java projects, the codebase for msf4j
is impenetrably complex. The overriding goal of this project is to have a codebase that is easy to
reason about. This should have two effects: it will be easy to maintain and modify, and it will be
easy for users of this framework to find answers to their questions in the code without requiring
a shaman to interpret it for them.

As for performance, 9 times out of 10, simple code will also be highly performant, but for the 1
time it isn't, optimizations will be made guided by profiling rather than superstition and will be
carefully documented in the code explaining how the code improves performance. On top of that, this
framework is being designed without the use of Annotations and Reflection which greatly simplifies
using AOT Compilation and GraalVM's Native Image which are both critical performance optimization
tools when it comes to building microservices.

Lastly, there is no intention to make this framework compatible with Spring or any other such
framework, though the open design should allow users to use whichever third-party libraries they
like while implementing services with this framework. It should be noted that libraries making
liberal use of Reflection will negate the benefit to AOT Compilation and GraalVM's Native Image.

## Contributing

TODO

Copyright (c) Michael Juliano 2020
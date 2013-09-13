## corespring-rhino

This is a fork of [Mozilla Rhino](https://github.com/mozilla/rhino) at the [Rhino1_7R4_RELEASE](https://github.com/mozilla/rhino/tree/Rhino1_7R4_RELEASE) tag.

The rationale for the existence of this fork is that we've got too many dependencies which use Rhino (namely [yuicompressor](https://github.com/yui/yuicompressor/) and [Play](https://github.com/playframework/playframework/) itself), that we needed to put rhino in a separate namespace to avoid unusal collisions of behavior.

This fork should ultimately be deprecated in the interest of figuring out a sane way to manage dependent versions of Rhino.

# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## 2.0.1

### Added

- Added new ifSuccessOrElse method to Try to be able to specify in one go the ```valueConsumer``` and ```exceptionConsumer```

## 2.0.0

### Added

- New class `UnsafeFunctions` to allow for easier and more readable access to the functionality
- `ThrowableRunnable` variant of the `Runnable` interface

### Changed

- Corrected Javadocs
- **BREAKING**: `ThrowableBinaryOperator` now properly implements `BinaryOperator` interface instead of
  `BinaryOperator`'s parent `BiFunction` interface
- **BREAKING**: `ThrowableUnaryOperator` now properly implements `UnaryOperator` interface instead of `UnaryOperator`'s
  parent `Function` interface

## 1.0.3

### Changed

- Removed restrictions for Try.Success, allowing to save `null` as successful value

## 1.0.2

### Changed

- Restored Java 8 build compatibility
- Adjusted license year
- Improved examples in README
- Fixed minor Sonar issues in tests

## 1.0.1

### Added

- Maven Central repository support

## 1.0.0

### Added

- Initial release with a set of Java functional interfaces and Try
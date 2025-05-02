import subprocess
import sys


def compile_java():
    result = subprocess.run([
        "javac", "-d", "src", "src/arbitraryarithmetic/AInteger.java",
        "src/arbitraryarithmetic/AFloat.java", "src/MyInfArith.java"
    ], capture_output=True, text=True)

    if result.returncode != 0:
        print("Compilation failed:")
        print(result.stderr)
        sys.exit(1)

def run_java(args):
    result = subprocess.run(
        ["java", "-cp", "src", "MyInfArith"] + args,
        capture_output=True, text=True
    )

    if result.returncode != 0:
        print("Runtime error:")
        print(result.stderr)
    else:
        print(result.stdout.strip())

def main():
    if len(sys.argv) != 5:
        print("Usage: python3 run_project.py [int|float] [add|sub|mul|div] num1 num2")
        sys.exit(1)

    compile_java()
    run_java(sys.argv[1:])

if __name__ == "__main__":
    main()



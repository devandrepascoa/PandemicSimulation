#!/bin/sh
JLINK_VM_OPTIONS=
DIR=`dirname $0`
$DIR/java $JLINK_VM_OPTIONS -m PandemicSimulation/com.devandrepascoa.main.PandemicSimulatorFix $@

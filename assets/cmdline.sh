#!/system/bin/sh
#Features: 
#maxkhz/minkhz/gov/maxscroff added to the kernels cmdline
#clean cmdline of foreigns in case of something wicked is going on in there
#(supports my kernel edits, so that the kernel boots with that values)



##Get CPU MINCLOCK 
    minkhz=$1
##end Get cpu minclock from aroma tmp

##Get CPU MAXCLOCK 
    maxkhz=$2
##end Get cpu maxclock from aroma tmp

##Get CPU max screen off clock
    maxscroff=$3
##end Get CPU max screen off clock from aroma tmp


##Get 3dgpuoc
    gpu3d=$4
##end Get 3dgpuoc from aroma tmp

##Get 2dgpuoc
    gpu2d=$5
##end Get 2dgpuoc from aroma tmp

##Get governor
    gov=$6
##end Get governor from aroma tmp

#clean cmdline from foreigns. failsafe
#needed since some cmdlines are full of rubbish :)
sed -i 's/no_console_suspend=1[^$]*$/no_console_suspend=1/g' /data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img-cmdline

#Add maxkhz to the kernels cmdline.
cmdline=$(cat /data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img-cmdline)
searchString="maxkhz="
maxkhz="maxkhz="$maxkhz
case $cmdline in
  "$searchString"* | *" $searchString"*)
   	echo $(cat /data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img-cmdline | sed -e 's/maxkhz=[^ ]\+//')>/data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img-cmdline
	echo $(cat /data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img-cmdline)\ $maxkhz>/data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img-cmdline
	;;  
  *)
	echo $(cat /data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img-cmdline)\ $maxkhz>/data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img-cmdline
	;;
esac
#end maxkhz

#Add minkhz to the kernels cmdline.
cmdline=$(cat /data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img-cmdline)
searchString="minkhz="
minkhz="minkhz="$minkhz
case $cmdline in
  "$searchString"* | *" $searchString"*)
   	echo $(cat /data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img-cmdline | sed -e 's/minkhz=[^ ]\+//')>/data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img-cmdline
	echo $(cat /data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img-cmdline)\ $minkhz>/data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img-cmdline
	;;  
  *)
	echo $(cat /data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img-cmdline)\ $minkhz>/data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img-cmdline
	;;
esac
#end minkhz

#Add gov to the kernels cmdline.
cmdline=$(cat /data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img-cmdline)
searchString="gov="
gov="gov="$gov
case $cmdline in
  "$searchString"* | *" $searchString"*)
   	echo $(cat /data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img-cmdline | sed -e 's/gov=[^ ]\+//')>/data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img-cmdline
	echo $(cat /data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img-cmdline)\ $gov>/data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img-cmdline
	;;  
  *)
	echo $(cat /data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img-cmdline)\ $gov>/data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img-cmdline
	;;
esac
#end gov

#Add maxscroff to the kernels cmdline.
cmdline=$(cat /data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img-cmdline)
searchString="maxscroff="
maxscroff="maxscroff="$maxscroff
case $cmdline in
  "$searchString"* | *" $searchString"*)
   	echo $(cat /data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img-cmdline | sed -e 's/maxscroff=[^ ]\+//')>/data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img-cmdline
	echo $(cat /data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img-cmdline)\ $maxscroff>/data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img-cmdline
	;;  
  *)
	echo $(cat /data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img-cmdline)\ $maxscroff>/data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img-cmdline
	;;
esac
#end maxscroff

#Add 3dgpu to the kernels cmdline.
cmdline=$(cat /data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img-cmdline)
searchString="3dgpu="
gpu3d="3dgpu="$gpu3d
case $cmdline in
  "$searchString"* | *" $searchString"*)
   	echo $(cat /data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img-cmdline | sed -e 's/3dgpu=[^ ]\+//')>/data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img-cmdline
	echo $(cat /data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img-cmdline)\ $gpu3d>/data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img-cmdline
	;;  
  *)
	echo $(cat /data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img-cmdline)\ $gpu3d>/data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img-cmdline
	;;
esac
#end 3dgpu

#Add 2dgpu to the kernels cmdline.
cmdline=$(cat /data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img-cmdline)
searchString="2dgpu="
gpu2d="2dgpu="$gpu2d
case $cmdline in
  "$searchString"* | *" $searchString"*)
   	echo $(cat /data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img-cmdline | sed -e 's/2dgpu=[^ ]\+//')>/data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img-cmdline
	echo $(cat /data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img-cmdline)\ $gpu2d>/data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img-cmdline
	;;  
  *)
	echo $(cat /data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img-cmdline)\ $gpu2d>/data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img-cmdline
	;;
esac
#end 2dgpu


Name:		jpacman
Version:	1.3
Release:	1
Summary:	A java swing implementation of pacman.

Group:		Amusements/Games
License:	GPL
URL:		http://github.com/snowpuppy/jPacman
Source0:	http://github.com/snowpuppy/jPacman/archive/v%{version}.tar.gz

BuildRequires:	java-devel
Requires:	java

%global debug_package %{nil}

%description
The jPacman game lets you play pacman with java and swing.
If you modify the code, you can add strange AI behaviors
for the ghosts. :)

%prep
%setup -q -n jPacman-%{version}


%build
cd src/
make


%install
rm -rf $RPM_BUILD_ROOT

cd src/
mkdir -p $RPM_BUILD_ROOT/usr/bin
mkdir -p $RPM_BUILD_ROOT/usr/lib
%make_install

%files
%doc README
/usr/lib/PacmanGame.jar
/usr/bin/pacmangame


%changelog


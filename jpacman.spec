Name:		jpacman
Version:	1.0.0
Release:	1
Summary:	A java swing implementation of pacman.

Group:		Amusements/Games
License:	GPL
URL:		http://github.com/snowpuppy/jPacman
Source0:	http://github.com/snowpuppy/jPacman/archive/v1.0.tar.gz

BuildRequires:	java-devel
Requires:	java

%global debug_package %{nil}

%description
The jPacman game lets you play pacman with java and swing.
If you modify the code, you can add strange AI behaviors
for the ghosts. :)

%prep
%setup -q -n jPacman-1.0


%build
cd src/
make


%install
rm -rf $RPM_BUILD_ROOT

%make_install

%files
%doc README



%changelog


import {Component, OnDestroy, Inject} from '@angular/core';
import {DOCUMENT} from '@angular/common';
import {navItems} from '../../_nav';
import {AuthenticationService} from "../../services/authentication.service";


@Component({
	selector: 'app-dashboard',
	templateUrl: './default-layout.component.html'
})
export class DefaultLayoutComponent implements OnDestroy {
	public navItems = navItems;
	public sidebarMinimized = true;
	private changes: MutationObserver;
	public element: HTMLElement;

	constructor(private authenticationService: AuthenticationService,
				@Inject(DOCUMENT) _document?: any)
	{
		this.changes = new MutationObserver((mutations) => {
			this.sidebarMinimized = _document.body.classList.contains('sidebar-minimized');
		});
		this.element = _document.body;
		this.changes.observe(<Element>this.element, {
			attributes: true,
			attributeFilter: ['class']
		});
	}

	ngOnDestroy(): void {
		this.changes.disconnect();
	}

	logout() { this.authenticationService.logout() }

	currentUser(): string { return this.authenticationService.currentUserValue.username }
}
